package com.example.weather_app_iti.view_model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app_iti.model.local.Alert
import com.example.weather_app_iti.model.local.CurrentWeatherData
import com.example.weather_app_iti.model.local.FavouriteCity
import com.example.weather_app_iti.model.repo.IRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class WeatherViewModel(private var repo: IRepository):ViewModel() {
    private val favCities:MutableStateFlow<MutableList<FavouriteCity>> =MutableStateFlow(mutableListOf())
    var fav_cities:StateFlow<MutableList<FavouriteCity>> =favCities
   private val alerts:MutableStateFlow<MutableList<Alert>> = MutableStateFlow(mutableListOf())
    var _alerts:StateFlow<MutableList<Alert>> =alerts
    private var weatherData= MutableStateFlow<ApiState>(ApiState.Loading)
    var _weatherData:StateFlow<ApiState> =weatherData
    private var currentWeatherData= MutableStateFlow<ApiState>(ApiState.Loading)
    var _currentWeatherData:StateFlow<ApiState> =currentWeatherData
     fun getAlerts(){
         viewModelScope.launch {
             repo.getAlerts().collect{
                 alerts.emit(it)

             }
         }

    }
      fun getFavourites(){
          viewModelScope.launch {
              repo.getFavourites().collect{
                 favCities.emit(it)
              }
          }
    }

     @RequiresApi(Build.VERSION_CODES.O)
    fun getWeatherData(
        lat: String,
        lon: String,
        key: String,
        units: String,
        lang: String,
        fav: Boolean
    ) {
         viewModelScope.launch(Dispatchers.IO) {
          repo.getWeatherData(lat,lon,key,units,lang,fav).catch {
              weatherData.emit(ApiState.Failure(it))
          }.collect{
             weatherData.emit(ApiState.Success(it))
          }
         }
    }

   fun getCurrentWeatherData(id: String, fav: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getCurrentWeatherData(id, fav).catch {
                currentWeatherData.emit(ApiState.Failure(it))
            } .collect{
                    currentWeatherData.emit(ApiState.Success(it))
            }
        }
    }

    fun insertCurrentWeatherData(currentWeatherData: CurrentWeatherData) {
        viewModelScope.launch {
            repo.insertCurrentWeatherData(currentWeatherData)
        }
    }

     fun insertFavourite(favouriteCity: FavouriteCity) {
         viewModelScope.launch {
             repo.insertFavourite(favouriteCity)
         }
     }

     fun insertAlert(alert: Alert) {
         viewModelScope.launch {
             repo.insertAlert(alert)
         }
     }

     fun deleteCurrentWeatherData(id: String) {
         viewModelScope.launch {
             repo.deleteCurrentWeatherData(id)
         }
    }

     fun deleteFavourite(favouriteCity: FavouriteCity) {
         viewModelScope.launch {
             repo.deleteFavourite(favouriteCity)
         }
    }

    fun deleteAlert(alert: Alert) {
        viewModelScope.launch {
            repo.deleteAlert(alert)
        }    }

}
