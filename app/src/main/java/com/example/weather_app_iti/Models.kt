package com.example.weather_app_iti

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class CurrentWeatherData(
    @PrimaryKey
    var id:String,
    var lon: Double,
    var lat: Double,
    var temperature: String,
    var date: String,
    var time: String,
    var humidity: String,
    var windSpeed: String,
    var preesure: String,
    var clouds: String,
    var city: String,
    var icon: String,
    var weatherDescription: String,
    var fav:Boolean,
    var list3hours: MutableList<List3hours>,
    var list5days: MutableList<List5days>
)
@Entity(tableName = "fav_table")
data class FavouriteCity(
    @PrimaryKey
    var id:String,
    var lon: Double,
    var lat: Double,
    var city: String,
)
@Entity(tableName = "alert_table")
data class Alert(
    @PrimaryKey
    var id:String,
    var date: String,
    var time: String,
)
data class List3hours(
    var time:String,
    var icon: String,
    var temp:String
)
data class List5days(
    var date:String,
    var weatherDescription:String,
    var icon: String,
    var temp:String
)