package com.example.weather_app_iti

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("select * from fav_table")
    suspend fun getFavourites(): MutableList<FavouriteCity>
    @Query("select * from alert_table")
    suspend fun getAlerts(): MutableList<Alert>
    @Query("select * from weather_table where id=:id and fav=:fav")
    suspend fun getCurrentWeatherData(id:String, fav:Boolean): CurrentWeatherData
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCurrentWeatherData(currentWeatherData: CurrentWeatherData)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavourite(favouriteCity: FavouriteCity)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlert(alert: Alert)
    @Query("delete from weather_table where id=:id")
    suspend fun deleteCurrentWeatherData(id:String)
    @Delete
    suspend fun deleteFavourite(favouriteCity: FavouriteCity)
    @Delete
    suspend fun deleteAlert(alert: Alert)
}