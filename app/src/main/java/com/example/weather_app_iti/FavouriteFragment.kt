package com.example.weather_app_iti

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mvvm.view_model.WeatherViewModelFactory
import com.example.weather_app_iti.databinding.FragmentFavouriteBinding
import com.example.weather_app_iti.view_model.WeatherViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavouriteFragment : Fragment() {
    lateinit var binding:FragmentFavouriteBinding
    lateinit var adapter: FavAdapter
    lateinit var viewModel:WeatherViewModel
    lateinit var factory:WeatherViewModelFactory
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        viewModel.getFavourites()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentFavouriteBinding.inflate(inflater,container,false)
        lifecycleScope.launch {
        viewModel.fav_cities.collectLatest {
            adapter = FavAdapter(it,{
                    val fragment=HomeFragment()
                val bundle=Bundle()
                bundle.putDouble("lat",it.lat)
                bundle.putDouble("lon",it.lon)
                bundle.putBoolean("fav",true)
                bundle.putString("city",it.city)
                fragment.arguments=bundle
                requireActivity().supportFragmentManager.beginTransaction().
                replace(R.id.nav_host_fragment,fragment).commit()
            },{
                viewModel.deleteFavourite(it)
            })
            binding.favRecycle.adapter=adapter
        }
        }
        binding.addFav.setOnClickListener {
            if(sharedPreferences.getString(Setting.locationKey,getString(R.string.Gps))==getString(R.string.Map)) {
                val fragment = MapsFragment()
                fragment.label = "Favourite"
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment).commit()
            }else{
                Toast.makeText(requireContext(),"enable Map Location from Setting",Toast.LENGTH_LONG).show()
            }
        }
        return binding.root
    }
    private fun setupViewModel(){
        factory = WeatherViewModelFactory(
            Repository(
                RemoteDataSource(), LocalDataSource(requireContext())
            )
        )
        viewModel = ViewModelProvider(this, factory)[WeatherViewModel::class.java]
        sharedPreferences = requireActivity().getSharedPreferences("setup_setting",Context.MODE_PRIVATE)
    }


}