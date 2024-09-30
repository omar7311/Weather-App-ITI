package com.example.weather_app_iti.model.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weather_app_iti.model.local.Alert
import com.example.weather_app_iti.model.local.CurrentWeatherData
import com.example.weather_app_iti.model.local.FavouriteCity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("select * from fav_table")
    fun getFavourites(): Flow<MutableList<FavouriteCity>>
    @Query("select * from alert_table")
    fun getAlerts(): Flow<MutableList<Alert>>
    @Query("select * from weather_table where id=:id and fav=:fav")
    fun getCurrentWeatherData(id:String, fav:Boolean): Flow<CurrentWeatherData>
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