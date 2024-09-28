package com.example.weather_app_iti

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
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
import com.example.weather_app_iti.view_model.WeatherViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Locale

class MapsFragment : Fragment() {
     var label=""

    @RequiresApi(Build.VERSION_CODES.O)
    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.setOnMapClickListener {
            val lat= it.latitude
            val lon=it.longitude
            googleMap.clear()
            googleMap.addMarker(MarkerOptions().position(it).title(getCityFromLocation(lat,lon)))
            showConfirmationBottomSheet(lat, lon)

        }
        val sydney = LatLng(29.9737, 31.2544)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Maddi , Cairo"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(requireActivity(),label,Toast.LENGTH_LONG).show()
        return inflater.inflate(R.layout.fragment_maps, container, false)
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
                    val fragment=HomeFragment()
                    val bundle=Bundle()
                    bundle.putDouble("lat",lat)
                    bundle.putDouble("lon",lon)
                    bundle.putBoolean("fav",false)
                    fragment.arguments=bundle
                    requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment,fragment).commit()
                    (activity as AppCompatActivity).supportActionBar?.title=getString(R.string.Home)

                }
                "favourite"->{
                    requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment,FavouriteFragment()).commit()
                }
            }
        }

    }

    private fun getCityFromLocation(latitude: Double, longitude: Double) :String {
        val geocoder = Geocoder(requireActivity(), Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                 return addresses[0].adminArea
            } else {
               return "no city found"
            }
        } catch (e: Exception) {
           return e.message?:""
        }
    }
}