package com.example.weather_app_iti

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalDataSource(private val context: Context) : ILocalDataSource {
     override fun getFavourites(): Flow<MutableList<FavouriteCity>> {
     return WeatherDatabase.getInstance(context).getWeatherDao().getFavourites()
    }
     override fun getAlerts(): Flow<MutableList<Alert>> {
         return WeatherDatabase.getInstance(context).getWeatherDao().getAlerts()
    }
     override fun getCurrentWeatherData(id: String, fav: Boolean): Flow<CurrentWeatherData>{
         return WeatherDatabase.getInstance(context).getWeatherDao().getCurrentWeatherData(id,fav)
    }
     override suspend fun insertCurrentWeatherData(currentWeatherData: CurrentWeatherData) {
         WeatherDatabase.getInstance(context).getWeatherDao().insertCurrentWeatherData(currentWeatherData)
    }
     override suspend fun insertFavourite(favouriteCity: FavouriteCity) {
         WeatherDatabase.getInstance(context).getWeatherDao().insertFavourite(favouriteCity)

     }
     override suspend fun insertAlert(alert: Alert) {
         WeatherDatabase.getInstance(context).getWeatherDao().insertAlert(alert)
     }
     override suspend fun deleteCurrentWeatherData(id: String) {
         WeatherDatabase.getInstance(context).getWeatherDao().deleteCurrentWeatherData(id)
     }
     override suspend fun deleteFavourite(favouriteCity: FavouriteCity) {
         WeatherDatabase.getInstance(context).getWeatherDao().deleteFavourite(favouriteCity)

     }
    override suspend fun deleteAlert(alert: Alert) {
        WeatherDatabase.getInstance(context).getWeatherDao().deleteAlert(alert)

    }
}