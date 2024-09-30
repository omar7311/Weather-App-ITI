package com.example.weather_app_iti.view_model

import androidx.lifecycle.viewModelScope
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mvvm.view_model.WeatherViewModelFactory
import com.example.weather_app_iti.Alert
import com.example.weather_app_iti.CurrentWeatherData
import com.example.weather_app_iti.FakeRepository
import com.example.weather_app_iti.FavouriteCity
import com.example.weather_app_iti.IRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherViewModelTest {
    lateinit var repository: FakeRepository
    lateinit var factory: WeatherViewModelFactory
    lateinit var viewModel: WeatherViewModel
    val fav= FavouriteCity("369", 12.2589, 50.2587, "cairo")
    val alert= Alert("123", "2024:2:1", "12:50:10")
    private  val list1= mutableListOf(fav)
    private  val list2= mutableListOf(alert)
    @Before
    fun setup() {
        repository = FakeRepository(list1, list2)
        factory = WeatherViewModelFactory(repository)
        viewModel = WeatherViewModel(repository)
    }

    @Test
    fun getAlerts() {
        viewModel.getAlerts()
        assertEquals(viewModel._alerts.value,list2)
    }

    @Test
    fun getFavourites() {
        viewModel.getFavourites()
        assertEquals(viewModel.fav_cities.value, list1)
    }

    @Test
    fun insertFavourite() {
       val fav= FavouriteCity("369", 12.2589, 50.2587, "cairo")
        viewModel.insertFavourite(fav)
        assertEquals(repository.list1.last(),fav)
    }

    @Test
    fun insertAlert() {
       val alert= Alert("123", "2024:2:1", "12:50:10")
        viewModel.insertAlert(alert)
        assertEquals(repository.list2.last(),alert)
    }

}