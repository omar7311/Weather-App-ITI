package com.example.weather_app_iti.view_model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app_iti.Alert
import com.example.weather_app_iti.CurrentWeatherData
import com.example.weather_app_iti.FavouriteCity
import com.example.weather_app_iti.IRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private var repo: IRepository):ViewModel() {
    private val favCities:MutableLiveData<MutableList<FavouriteCity>> =MutableLiveData()
    var fav_cities:LiveData<MutableList<FavouriteCity>> =favCities
   private val alerts:MutableLiveData<List<Alert>> = MutableLiveData<List<Alert>>()
    var _alerts:LiveData<List<Alert>> =alerts
    private val currentWeatherData:MutableLiveData<CurrentWeatherData> = MutableLiveData()
    var _currentWeatherData:LiveData<CurrentWeatherData> =currentWeatherData

     fun getAlerts(){
         viewModelScope.launch {
             repo.getAlerts().collect{
                 alerts.postValue(it)
             }
         }

    }
      fun getFavourites(){
          viewModelScope.launch {
              repo.getFavourites().collect{
                 favCities.postValue(it)
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
         viewModelScope.launch {
           currentWeatherData.postValue(repo.getWeatherData(lat,lon,key,units,lang,fav))
         }
    }

    fun getCurrentWeatherData(id: String, fav: Boolean) {
        viewModelScope.launch {
           currentWeatherData.postValue(repo.getCurrentWeatherData(id,fav))
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
