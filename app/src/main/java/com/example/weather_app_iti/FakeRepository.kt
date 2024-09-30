package com.example.weather_app_iti

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository(var list1:MutableList<FavouriteCity>,var list2:MutableList<Alert>) : IRepository {
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

    override fun getFavourites(): Flow<MutableList<FavouriteCity>> {
        val list = flow {
            val fav = mutableListOf<FavouriteCity>()
            fav.add(FavouriteCity("369", 12.2589, 50.2587, "cairo"))
            emit(fav)
        }
        return list
    }

    override fun getAlerts(): Flow<MutableList<Alert>> {
        val list = flow<MutableList<Alert>> {
            val alerts = mutableListOf<Alert>()
            alerts.add(Alert("123", "2024:2:1", "12:50:10"))
            emit(alerts)
        }
        return list
    }

    override fun getCurrentWeatherData(id: String, fav: Boolean): Flow<CurrentWeatherData> {
        TODO("Not yet implemented")
    }

    override suspend fun insertCurrentWeatherData(currentWeatherData: CurrentWeatherData) {
        TODO("Not yet implemented")
    }




    override suspend fun insertFavourite(favouriteCity: FavouriteCity) {
        list1.add(favouriteCity)

    }

    override suspend fun insertAlert(alert: Alert) {
        list2.add(alert)
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