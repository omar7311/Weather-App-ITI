package com.example.weather_app_iti.model.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("weather")
    suspend fun getCurrentWeather(@Query("lat") lat:String,
                                   @Query("lon") lon:String,
                                   @Query("appid") key:String,
                                   @Query("units") units:String,
                                   @Query("lang") lang:String): ResponseCurrentWeather

    @GET("forecast")
    suspend  fun get5days3hoursForecast(@Query("lat") lat:String,
                                   @Query("lon") lon:String,
                                   @Query("appid") key:String,
                                   @Query("units") units:String,
                                   @Query("lang") lang:String): Response5days3hours
}