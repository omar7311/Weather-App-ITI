package com.example.weather_app_iti.model.remote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weather_app_iti.model.local.Alert
import com.example.weather_app_iti.model.local.Converters
import com.example.weather_app_iti.model.local.CurrentWeatherData
import com.example.weather_app_iti.model.local.FavouriteCity
import com.example.weather_app_iti.model.local.WeatherDao

@Database(entities = arrayOf(CurrentWeatherData::class, FavouriteCity::class, Alert::class), version = 1)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao
    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null
        fun getInstance(ctx: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, WeatherDatabase::class.java, "weather_database"
                )
                    .build()
                INSTANCE = instance
// return instance
                instance
            }
        }
    }
}