package com.example.weather_app_iti

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow

interface IRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getWeatherData(
        lat: String,
        lon: String,
        key: String,
        units: String,
        lang: String,
        fav: Boolean
    ): CurrentWeatherData

    suspend fun getFavourites(): Flow<MutableList<FavouriteCity>>
    suspend fun getAlerts(): Flow<MutableList<Alert>>
    suspend fun getCurrentWeatherData(id: String, fav: Boolean): CurrentWeatherData

    suspend fun insertCurrentWeatherData(currentWeatherData: CurrentWeatherData)

    suspend fun insertFavourite(favouriteCity: FavouriteCity)

    suspend fun insertAlert(alert: Alert)

    suspend fun deleteCurrentWeatherData(id: String)

    suspend fun deleteFavourite(favouriteCity: FavouriteCity)

    suspend fun deleteAlert(alert: Alert)
}