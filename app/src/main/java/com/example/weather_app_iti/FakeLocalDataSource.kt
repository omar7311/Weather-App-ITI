package com.example.weather_app_iti

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalDataSource(var favList: MutableList<FavouriteCity>,
                          var alertList:MutableList<Alert>) :ILocalDataSource {
    override fun getFavourites(): Flow<MutableList<FavouriteCity>> {
        return flow<MutableList<FavouriteCity>> {
            emit(favList)
        }
    }

    override fun getAlerts(): Flow<MutableList<Alert>> {
        return flow {
            emit(alertList)
        }
    }

    override fun getCurrentWeatherData(id: String, fav: Boolean): Flow<CurrentWeatherData> {
        TODO("Not yet implemented")
    }

    override suspend fun insertCurrentWeatherData(currentWeatherData: CurrentWeatherData) {
        TODO("Not yet implemented")
    }

    override suspend fun insertFavourite(favouriteCity: FavouriteCity) {
        favList.add(favouriteCity)
    }

    override suspend fun insertAlert(alert: Alert) {
        alertList.add(alert)
    }

    override suspend fun deleteCurrentWeatherData(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFavourite(favouriteCity: FavouriteCity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAlert(alert: Alert) {
        TODO("Not yet implemented")
    }
}