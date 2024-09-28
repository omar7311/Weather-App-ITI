package com.example.weather_app_iti

import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {
    suspend fun getFavourites(): MutableList<FavouriteCity>

    suspend fun getAlerts(): MutableList<Alert>

    fun getCurrentWeatherData(id: String, fav: Boolean): Flow<CurrentWeatherData>

    suspend fun insertCurrentWeatherData(currentWeatherData: CurrentWeatherData)

    suspend fun insertFavourite(favouriteCity: FavouriteCity)

    suspend fun insertAlert(alert: Alert)

    suspend fun deleteCurrentWeatherData(id: String)

    suspend fun deleteFavourite(favouriteCity: FavouriteCity)

    suspend fun deleteAlert(alert: Alert)
}