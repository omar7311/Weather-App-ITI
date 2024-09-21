package com.example.weather_app_iti

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi

interface IRemoteDataSource {
    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getWeatherData(
        lat: String,
        lon: String,
        key: String,
        units: String,
        lang: String,
        fav: Boolean
    ): CurrentWeatherData
}