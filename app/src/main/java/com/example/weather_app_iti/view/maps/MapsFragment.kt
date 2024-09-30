package com.example.weather_app_iti.view.maps

import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.view_model.WeatherViewModelFactory
import com.example.weather_app_iti.model.local.FavouriteCity
import com.example.weather_app_iti.R
import com.example.weather_app_iti.model.remote.RemoteDataSource
import com.example.weather_app_iti.model.repo.Repository
import com.example.weather_app_iti.model.local.LocalDataSource
import com.example.weather_app_iti.view.favourite.FavouriteFragment
import com.example.weather_app_iti.view.home.HomeFragment
import com.example.weather_app_iti.view_model.WeatherViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Locale

class MapsFragment : Fragment() {
     var label=""
lateinit var viewModel: WeatherViewModel
lateinit var factory: WeatherViewModelFactory
    @RequiresApi(Build.VERSION_CODES.O)
    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.setOnMapClickListener {
            val lat= truncateToFourDecimalPlaces(it.latitude)
            val lon=truncateToFourDecimalPlaces(it.longitude)
            googleMap.clear()
           googleMap.addMarker(MarkerOptions().position(it).title(getCityFromLocation(lat,lon)))
           showConfirmationBottomSheet(lat, lon)

        }
        val sydney = LatLng(29.9737, 31.2544)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Maddi , Cairo"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
    fun truncateToFourDecimalPlaces(num: Double): Double {
        val factor = 10000.0
        return (num * factor).toLong() / factor
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViewModel()
        Toast.makeText(requireActivity(),label,Toast.LENGTH_LONG).show()
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }
    private fun setupViewModel(){
        factory = WeatherViewModelFactory(
            Repository(
                RemoteDataSource(), LocalDataSource(requireContext())
            )
        )
        viewModel = ViewModelProvider(this, factory).get(WeatherViewModel::class.java)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SuspiciousIndentation", "InflateParams", "MissingInflatedId")
    private fun showConfirmationBottomSheet(lat:Double,lon:Double) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
       bottomSheetDialog.setContentView(view)
         view.findViewById<TextView>(R.id.selected_city).text=getCityFromLocation(lat,lon)
        bottomSheetDialog.show()
        view.findViewById<Button>(R.id.save).setOnClickListener {
            bottomSheetDialog.dismiss()
            when(label){
                "Setting"->{
                    val fragment= HomeFragment()
                    val bundle=Bundle()
                    bundle.putDouble("lat",lat)
                    bundle.putDouble("lon",lon)
                    bundle.putBoolean("fav",false)
                    fragment.arguments=bundle
                    requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment,fragment).commit()
                    (activity as AppCompatActivity).supportActionBar?.title=getString(R.string.Home)

                }
                "Favourite"->{
                    viewModel.insertFavourite(FavouriteCity("$lat$lon",lon,lat,getCityFromLocation(lat,lon)))
                    requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, FavouriteFragment()).commit()
                }
            }
        }

    }

    private fun getCityFromLocation(latitude: Double, longitude: Double) :String {
        val geocoder = Geocoder(requireActivity(), Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                 return addresses[0].subAdminArea
            } else {
               return "no city found"
            }
        } catch (e: Exception) {
           return e.message?:""
        }
    }
}