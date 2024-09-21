package com.example.weather_app_iti

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class RemoteDataSource : IRemoteDataSource {
    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeatherData(lat: String, lon: String, key: String, units: String, lang: String, fav:Boolean) :CurrentWeatherData{
        val responseCurrentWeather =
            RetrofitClient.apiServices.getCurrentWeather(lat, lon, key, units, lang)
        val response5days3hours =
            RetrofitClient.apiServices.get5days3hoursForecast(lat, lon, key, units, lang)
        val list3hours= mutableListOf<List3hours>()
        val list5days= mutableListOf<List5days>()
            list3hours.add(List3hours("12 Am",response5days3hours.list[0].weather[0].icon,response5days3hours.list[0].main.temp.toString()))
            list3hours.add(List3hours("3 Am",response5days3hours.list[1].weather[0].icon,response5days3hours.list[1].main.temp.toString()))
            list3hours.add(List3hours("6 Am",response5days3hours.list[2].weather[0].icon,response5days3hours.list[2].main.temp.toString()))
            list3hours.add(List3hours("9 Am",response5days3hours.list[3].weather[0].icon,response5days3hours.list[3].main.temp.toString()))
            list3hours.add(List3hours("12 Pm",response5days3hours.list[4].weather[0].icon,response5days3hours.list[4].main.temp.toString()))
            list3hours.add(List3hours("3 Pm",response5days3hours.list[5].weather[0].icon,response5days3hours.list[5].main.temp.toString()))
            list3hours.add(List3hours("6 Pm",response5days3hours.list[6].weather[0].icon,response5days3hours.list[6].main.temp.toString()))
            list3hours.add(List3hours("9 Pm",response5days3hours.list[7].weather[0].icon,response5days3hours.list[7].main.temp.toString()))
            list5days.add(List5days(LocalDate.of(LocalDate.now().year,LocalDate.now().monthValue,LocalDate.now().dayOfMonth+1).dayOfWeek.name,
                response5days3hours.list[8].weather[0].description,
                response5days3hours.list[8].weather[0].icon,
                response5days3hours.list[8].main.temp.toString()))
            list5days.add(List5days(LocalDate.of(LocalDate.now().year,LocalDate.now().monthValue,LocalDate.now().dayOfMonth+2).dayOfWeek.name,
                response5days3hours.list[15].weather[0].description,
                response5days3hours.list[15].weather[0].icon,
                response5days3hours.list[15].main.temp.toString()))
            list5days.add(List5days(LocalDate.of(LocalDate.now().year,LocalDate.now().monthValue,LocalDate.now().dayOfMonth+3).dayOfWeek.name,
                response5days3hours.list[23].weather[0].description,
                response5days3hours.list[23].weather[0].icon,
                response5days3hours.list[23].main.temp.toString()))
            list5days.add(List5days(LocalDate.of(LocalDate.now().year,LocalDate.now().monthValue,LocalDate.now().dayOfMonth+4).dayOfWeek.name,
                response5days3hours.list[31].weather[0].description,
                response5days3hours.list[31].weather[0].icon,
                response5days3hours.list[31].main.temp.toString()))
            list5days.add(List5days(LocalDate.of(LocalDate.now().year,LocalDate.now().monthValue,LocalDate.now().dayOfMonth+5).dayOfWeek.name,
                response5days3hours.list[39].weather[0].description,
                response5days3hours.list[39].weather[0].icon,
                response5days3hours.list[39].main.temp.toString()))
            return CurrentWeatherData(
                "${responseCurrentWeather.coord.lat}${responseCurrentWeather.coord.lon}",
                responseCurrentWeather.coord.lon,
                responseCurrentWeather.coord.lat,
                responseCurrentWeather.main.temp.toString(),
                LocalDate.now().dayOfWeek.name,
                LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")),
                responseCurrentWeather.main.humidity.toString(),
                responseCurrentWeather.wind.speed.toString(),
                responseCurrentWeather.main.pressure.toString(),
                responseCurrentWeather.clouds.all.toString(),
                responseCurrentWeather.name,
                responseCurrentWeather.weather[0].icon,
                responseCurrentWeather.weather[0].description,
                fav,
                list3hours,
                list5days
                )
    }
}