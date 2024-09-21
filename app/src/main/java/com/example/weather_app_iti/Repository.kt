package com.example.weather_app_iti

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.flow

class Repository(private val remoteDataSource: IRemoteDataSource,private val localDataSource: ILocalDataSource) :
    IRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeatherData(
        lat: String,
        lon: String,
        key: String,
        units: String,
        lang: String,
        fav: Boolean
    ): CurrentWeatherData {
        return remoteDataSource.getWeatherData(lat,lon,key,units,lang,fav)
    }

     override suspend fun getFavourites()= flow<MutableList<FavouriteCity>> {
         emit(localDataSource.getFavourites())
    }

     override suspend fun getAlerts()= flow<MutableList<Alert>> {
         emit(localDataSource.getAlerts())
    }

    override suspend fun getCurrentWeatherData(id: String, fav: Boolean): CurrentWeatherData {
        return localDataSource.getCurrentWeatherData(id, fav)
    }

     override suspend fun insertCurrentWeatherData(currentWeatherData: CurrentWeatherData) {
         localDataSource.insertCurrentWeatherData(currentWeatherData)
    }

     override suspend fun insertFavourite(favouriteCity: FavouriteCity) {
         localDataSource.insertFavourite(favouriteCity)
    }

    override suspend fun insertAlert(alert: Alert) {
        localDataSource.insertAlert(alert)
    }

    override suspend fun deleteCurrentWeatherData(id: String) {
        localDataSource.deleteCurrentWeatherData(id)
    }

    override suspend fun deleteFavourite(favouriteCity: FavouriteCity) {
        localDataSource.deleteFavourite(favouriteCity)
    }

    override suspend fun deleteAlert(alert: Alert) {
        localDataSource.deleteAlert(alert)
    }
}