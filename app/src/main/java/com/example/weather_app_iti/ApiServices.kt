package com.example.weather_app_iti

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {
    @GET("weather?lat={lat}&lon={lon}&appid={API key}&units={units}&lang={lang}")
    suspend  fun getCurrentWeather(@Path("lat") lat:Double,
                                   @Path("lon") lon:Double,
                                   @Path("API key") key:Long,
                                   @Path("units") units:String,
                                   @Path("lang") lang:String): ResponseCurrentWeather

    @GET("forecast?lat={lat}&lon={lon}&appid={API key}&units={units}&lang={lang}")
    suspend  fun get5days3hoursForecast(@Path("lat") lat:Double,
                                   @Path("lon") lon:Double,
                                   @Path("API key") key:Long,
                                   @Path("units") units:String,
                                   @Path("lang") lang:String): Response5days3hours
}