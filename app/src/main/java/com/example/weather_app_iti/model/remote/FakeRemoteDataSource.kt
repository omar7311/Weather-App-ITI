package com.example.weather_app_iti.model.remote

import com.example.weather_app_iti.model.local.CurrentWeatherData
import kotlinx.coroutines.flow.Flow

class FakeRemoteDataSource: IRemoteDataSource {
    override fun getWeatherData(
        lat: String,
        lon: String,
        key: String,
        units: String,
        lang: String,
        fav: Boolean
    ): Flow<CurrentWeatherData> {
        TODO("Not yet implemented")
    }
}