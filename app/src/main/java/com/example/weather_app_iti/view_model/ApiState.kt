package com.example.weather_app_iti.view_model

import com.example.weather_app_iti.CurrentWeatherData

sealed class ApiState {
    class Success(var currentWeatherData: CurrentWeatherData?):ApiState()
    class Failure(var t: Throwable):ApiState()
    object Loading:ApiState()
}
