package com.example.mvvm.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather_app_iti.IRepository
import com.example.weather_app_iti.view_model.WeatherViewModel

class WeatherViewModelFactory(private val repo: IRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom( WeatherViewModel::class.java)){
            WeatherViewModel(repo) as T
        }else{
            throw IllegalArgumentException("view model can not found")
        }
    }
}