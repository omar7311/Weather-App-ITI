package com.example.weather_app_iti.model.remote

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weather_app_iti.model.local.CurrentWeatherData
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {
    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeatherData(
        lat: String,
        lon: String,
        key: String,
        units: String,
        lang: String,
        fav: Boolean
    ) : Flow<CurrentWeatherData>
}