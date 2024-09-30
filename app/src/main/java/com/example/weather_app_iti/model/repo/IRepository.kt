package com.example.weather_app_iti.model.repo

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weather_app_iti.model.local.Alert
import com.example.weather_app_iti.model.local.CurrentWeatherData
import com.example.weather_app_iti.model.local.FavouriteCity
import kotlinx.coroutines.flow.Flow

interface IRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeatherData(
        lat: String,
        lon: String,
        key: String,
        units: String,
        lang: String,
        fav: Boolean
    ): Flow<CurrentWeatherData>

     fun getFavourites(): Flow<MutableList<FavouriteCity>>
     fun getAlerts(): Flow<MutableList<Alert>>
     fun getCurrentWeatherData(id: String, fav: Boolean): Flow<CurrentWeatherData>

    suspend fun insertCurrentWeatherData(currentWeatherData: CurrentWeatherData)

    suspend fun insertFavourite(favouriteCity: FavouriteCity)

    suspend fun insertAlert(alert: Alert)

    suspend fun deleteCurrentWeatherData(id: String)

    suspend fun deleteFavourite(favouriteCity: FavouriteCity)

    suspend fun deleteAlert(alert: Alert)
}