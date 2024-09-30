package com.example.weather_app_iti.model.repo

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weather_app_iti.model.local.Alert
import com.example.weather_app_iti.model.local.CurrentWeatherData
import com.example.weather_app_iti.model.local.FavouriteCity
import com.example.weather_app_iti.model.local.ILocalDataSource
import com.example.weather_app_iti.model.remote.IRemoteDataSource
import com.example.weather_app_iti.model.repo.IRepository
import kotlinx.coroutines.flow.Flow

class Repository(private val remoteDataSource: IRemoteDataSource, private val localDataSource: ILocalDataSource) :
    IRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getWeatherData(
        lat: String,
        lon: String,
        key: String,
        units: String,
        lang: String,
        fav: Boolean
    ): Flow<CurrentWeatherData> {
        return remoteDataSource.getWeatherData(lat,lon,key,units,lang,fav)
    }

     override fun getFavourites(): Flow<MutableList<FavouriteCity>> {
         return localDataSource.getFavourites()
    }

     override fun getAlerts():Flow<MutableList<Alert>> {
         return localDataSource.getAlerts()
    }

    override fun getCurrentWeatherData(id: String, fav: Boolean): Flow<CurrentWeatherData> {
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