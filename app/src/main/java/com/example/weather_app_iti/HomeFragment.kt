package com.example.weather_app_iti

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.whenCreated
import com.bumptech.glide.Glide
import com.example.mvvm.view_model.WeatherViewModelFactory
import com.example.weather_app_iti.databinding.FragmentHomeBinding
import com.example.weather_app_iti.view_model.ApiState
import com.example.weather_app_iti.view_model.WeatherViewModel

import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: WeatherViewModel
    lateinit var factory: WeatherViewModelFactory
    lateinit var _3hoursAdapter: Weather3hoursAdapter
    lateinit var _5daysAdapter: Weather5daysAdapter
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission())
            { isGranted: Boolean ->
                if (isGranted) {
                    // Permission granted, proceed with location access
                    if (enableLocation()) {
                        getWeather()
                    } else {
                        enableLocationServices()
                    }
                } else {
                    // Permission denied, handle accordingly
                    MaterialAlertDialogBuilder(requireContext()).setTitle(getString(R.string.need_permission))
                        .setMessage(R.string.permission_message).setCancelable(false)
                        .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                        }.show()
                }
            }
        setupViewModel()
    }

    private fun setupViewModel() {
        factory = WeatherViewModelFactory(
            Repository(
                RemoteDataSource(), LocalDataSource(requireContext())
            )
        )
        viewModel = ViewModelProvider(this, factory).get(WeatherViewModel::class.java)
        sharedPreferences =
            requireActivity().getSharedPreferences("setup_setting", Context.MODE_PRIVATE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getLocationFromMap(lat: Double, lon: Double, fav: Boolean) {
        viewModel.getWeatherData(
            lat.toString(),
            lon.toString(),
            getString(R.string.weather_app_key),
            sharedPreferences.getString(Setting.unitsKey, getString(R.string.metric))!!,
            sharedPreferences.getString(Setting.languageKey, getString(R.string.en))!!,
            fav
        )
    }

    fun checkPermission(): Boolean {
        return checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun enableLocation(): Boolean {
        val locationManger =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManger.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManger.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun enableLocationServices() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }


    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        if (checkPermission()) {
            if (enableLocation()) {
                getWeather()
            } else {
                enableLocationServices()
            }
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isInternetAvailable(requireContext())) {
            Snackbar.make(view, getString(R.string.no_internet_connection), Snackbar.LENGTH_LONG)
                .show()
        }
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    private fun showNoData() {
        val inflater =
            requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val noDataView: View = inflater.inflate(R.layout.no_data_avaliable, null)
        binding.scrollView.removeAllViews()
        binding.scrollView.addView(noDataView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission")
    private fun getFreshLocationUpdated() {
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            val latitude = it.latitude.toString()
            val longitude = it.longitude.toString()
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getWeatherData(
                        latitude,
                        longitude,
                        getString(R.string.weather_app_key),
                        sharedPreferences.getString(Setting.unitsKey, getString(R.string.metric))!!,
                        sharedPreferences.getString(Setting.languageKey, getString(R.string.en))!!,
                        false
                    )
                }

            }
        }
    }

    private fun getCollectedRemoteData(cityName: String?) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel._weatherData.collectLatest {
                    when (it) {
                        is ApiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val result = it.currentWeatherData
                            if (result != null) {
                                binding.currentWeatherData = result
                                if (cityName != null) {
                                    binding.cityName.text = cityName
                                    result.city = cityName
                                }
                                _3hoursAdapter = Weather3hoursAdapter(
                                    requireContext(),
                                    result.list3hours
                                )
                                _5daysAdapter = Weather5daysAdapter(
                                    requireContext(),
                                    result.list5days
                                )
                                binding.temp3hours.adapter = _3hoursAdapter
                                binding.temp5days.adapter = _5daysAdapter
                                Setting.getImage(binding.icon, result.icon)

                                if (!result.fav) {
                                    viewModel.deleteCurrentWeatherData(result.id)
                                    viewModel.insertCurrentWeatherData(result)
                                    sharedPreferences.edit().putString(
                                        "lat",
                                        result.lat.toString()
                                    ).apply()
                                    sharedPreferences.edit().putString(
                                        "lon",
                                        result.lon.toString()
                                    ).apply()
                                } else {
                                    viewModel.insertCurrentWeatherData(result)
                                }
                            }
                        }

                        is ApiState.Failure -> {
                            binding.progressBar.visibility = View.GONE
                            MaterialAlertDialogBuilder(requireContext()).setTitle("Error")
                                .setMessage(it.t.message).setCancelable(false)
                                .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                                    dialog.dismiss()
                                }.show()
                        }

                        is ApiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                }

            }
        }
    }

    private fun getCollectedLocalData() {
        val lat = sharedPreferences.getString("lat", "")
        val lon = sharedPreferences.getString("lon", "")
        viewModel.getCurrentWeatherData(lat + lon, false)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel._currentWeatherData.collectLatest {
                    when (it) {
                        is ApiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            if (it.currentWeatherData != null) {
                                binding.currentWeatherData = it.currentWeatherData
                                _3hoursAdapter = Weather3hoursAdapter(
                                    requireContext(),
                                    it.currentWeatherData!!.list3hours
                                )
                                _5daysAdapter = Weather5daysAdapter(
                                    requireContext(),
                                    it.currentWeatherData!!.list5days
                                )
                                binding.temp3hours.adapter = _3hoursAdapter
                                binding.temp5days.adapter = _5daysAdapter
                                Setting.getImage(binding.icon, it.currentWeatherData?.icon ?: "")
                            } else {
                                showNoData()
                            }
                        }

                        is ApiState.Failure -> {
                            binding.progressBar.visibility = View.GONE
                            MaterialAlertDialogBuilder(requireContext()).setTitle("Error")
                                .setMessage(it.t.message).setCancelable(false)
                                .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                                    dialog.dismiss()
                                }.show()
                        }

                        is ApiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }

                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getWeather() {
        val location = sharedPreferences.getString(Setting.locationKey, getString(R.string.Gps))
        when (location) {
            getString(R.string.Gps) -> {
                if (isInternetAvailable(requireContext())) {
                    getFreshLocationUpdated()
                    getCollectedRemoteData(null)
                } else {
                    getCollectedLocalData()
                }
            }

            getString(R.string.Map) -> {
                val args = arguments
                if (args != null) {
                    if (isInternetAvailable(requireContext())) {
                        getLocationFromMap(
                            args.getDouble("lat"),
                            args.getDouble("lon"),
                            args.getBoolean("fav")
                        )
                        getCollectedRemoteData(args.getString("city"))
                    } else {
                        viewModel.getCurrentWeatherData(
                            args.getDouble("lat").toString() + args.getDouble("lon").toString(),
                            args.getBoolean("fav")
                        )
                        lifecycleScope.launch {
                            repeatOnLifecycle(Lifecycle.State.STARTED) {
                                viewModel._currentWeatherData.collectLatest {
                                    when (it) {
                                        is ApiState.Success -> {
                                            binding.progressBar.visibility = View.GONE
                                            if (it.currentWeatherData != null) {
                                                binding.currentWeatherData = it.currentWeatherData
                                                _3hoursAdapter = Weather3hoursAdapter(
                                                    requireContext(),
                                                    it.currentWeatherData!!.list3hours
                                                )
                                                _5daysAdapter = Weather5daysAdapter(
                                                    requireContext(),
                                                    it.currentWeatherData!!.list5days
                                                )
                                                binding.temp3hours.adapter = _3hoursAdapter
                                                binding.temp5days.adapter = _5daysAdapter
                                                Setting.getImage(
                                                    binding.icon,
                                                    it.currentWeatherData?.icon ?: ""
                                                )
                                            } else {
                                                showNoData()
                                            }
                                        }

                                        is ApiState.Failure -> {
                                            binding.progressBar.visibility = View.GONE
                                            MaterialAlertDialogBuilder(requireContext()).setTitle("Error")
                                                .setMessage(it.t.message).setCancelable(false)
                                                .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                                                    dialog.dismiss()
                                                }.show()
                                        }

                                        is ApiState.Loading -> {
                                            binding.progressBar.visibility = View.VISIBLE
                                        }
                                    }

                                }
                            }
                        }
                    }
                } else {
                    if (isInternetAvailable(requireContext())) {
                        val lat = sharedPreferences.getString("lat", "")
                        val lon = sharedPreferences.getString("lon", "")
                        getLocationFromMap(lat?.toDouble() ?: -34.0, lon?.toDouble() ?: 31.0, false)
                        getCollectedRemoteData(null)
                    } else {
                        getCollectedLocalData()
                    }
                }
            }
        }
    }
}