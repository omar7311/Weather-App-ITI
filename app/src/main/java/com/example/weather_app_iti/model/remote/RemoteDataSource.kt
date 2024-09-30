package com.example.weather_app_iti.model.remote

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weather_app_iti.model.local.CurrentWeatherData
import com.example.weather_app_iti.model.local.List3hours
import com.example.weather_app_iti.model.local.List5days
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class RemoteDataSource : IRemoteDataSource {
    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getWeatherData(
        lat: String,
        lon: String,
        key: String,
        units: String,
        lang: String,
        fav: Boolean
    ): Flow<CurrentWeatherData> = flow {
        val responseCurrentWeather =
            RetrofitClient.apiServices.getCurrentWeather(lat, lon, key, units, lang)
        val response5days3hours =
            RetrofitClient.apiServices.get5days3hoursForecast(lat, lon, key, units, lang)
        val list3hours = mutableListOf<List3hours>()
        val list5days = mutableListOf<List5days>()
        list3hours.add(
            List3hours(
                splitTime(response5days3hours.list[0].dt_txt),
                response5days3hours.list[0].weather[0].icon,
                response5days3hours.list[0].main.temp.toString()
            )
        )
        list3hours.add(
            List3hours(
                splitTime(response5days3hours.list[1].dt_txt),
                response5days3hours.list[1].weather[0].icon,
                response5days3hours.list[1].main.temp.toString()
            )
        )
        list3hours.add(
            List3hours(
                splitTime(response5days3hours.list[2].dt_txt),
                response5days3hours.list[2].weather[0].icon,
                response5days3hours.list[2].main.temp.toString()
            )
        )
        list3hours.add(
            List3hours(
                splitTime(response5days3hours.list[3].dt_txt),
                response5days3hours.list[3].weather[0].icon,
                response5days3hours.list[3].main.temp.toString()
            )
        )
        list3hours.add(
            List3hours(
                splitTime(response5days3hours.list[4].dt_txt),
                response5days3hours.list[4].weather[0].icon,
                response5days3hours.list[4].main.temp.toString()
            )
        )
        list3hours.add(
            List3hours(
                splitTime(response5days3hours.list[5].dt_txt),
                response5days3hours.list[5].weather[0].icon,
                response5days3hours.list[5].main.temp.toString()
            )
        )
        list3hours.add(
            List3hours(
                splitTime(response5days3hours.list[6].dt_txt),
                response5days3hours.list[6].weather[0].icon,
                response5days3hours.list[6].main.temp.toString()
            )
        )
        list3hours.add(
            List3hours(
                splitTime(response5days3hours.list[7].dt_txt),
                response5days3hours.list[7].weather[0].icon,
                response5days3hours.list[7].main.temp.toString()
            )
        )
        list5days.add(
            List5days(
                splitDate(response5days3hours.list[8].dt_txt),
                response5days3hours.list[8].weather[0].description,
                response5days3hours.list[8].weather[0].icon,
                response5days3hours.list[8].main.temp.toString()
            )
        )
        list5days.add(
            List5days(
                splitDate(response5days3hours.list[15].dt_txt),
                response5days3hours.list[15].weather[0].description,
                response5days3hours.list[15].weather[0].icon,
                response5days3hours.list[15].main.temp.toString()
            )
        )
        list5days.add(
            List5days(
                splitDate(response5days3hours.list[23].dt_txt),
                response5days3hours.list[23].weather[0].description,
                response5days3hours.list[23].weather[0].icon,
                response5days3hours.list[23].main.temp.toString()
            )
        )
        list5days.add(
            List5days(
                splitDate(response5days3hours.list[31].dt_txt),
                response5days3hours.list[31].weather[0].description,
                response5days3hours.list[31].weather[0].icon,
                response5days3hours.list[31].main.temp.toString()
            )
        )
        list5days.add(
            List5days(
               splitDate(response5days3hours.list[39].dt_txt),
                response5days3hours.list[39].weather[0].description,
                response5days3hours.list[39].weather[0].icon,
                response5days3hours.list[39].main.temp.toString()
            )
        )
        emit(
            CurrentWeatherData(
                "${responseCurrentWeather.coord.lat}${responseCurrentWeather.coord.lon}",
                responseCurrentWeather.coord.lon,
                responseCurrentWeather.coord.lat,
                responseCurrentWeather.main.temp.toString(),
                LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")),
                responseCurrentWeather.main.humidity.toString()+"%",
                responseCurrentWeather.wind.speed.toString(),
                responseCurrentWeather.main.pressure.toString()+"hPa",
                responseCurrentWeather.clouds.all.toString()+"%",
                responseCurrentWeather.name,
                responseCurrentWeather.weather[0].icon,
                responseCurrentWeather.weather[0].description,
                fav,
                list3hours,
                list5days
            )
        )
    }
@RequiresApi(Build.VERSION_CODES.O)
private fun splitDate(txt_date:String):String{
    val list1=txt_date.split(" ")
    val list2=list1[0].split("-")
    val list3= mutableListOf<Int>()
    for(i in list2) list3.add(i.toInt())
   return LocalDate.of(list3[0],list3[1],list3[2]).dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

}
    @RequiresApi(Build.VERSION_CODES.O)
    private fun splitTime(txt_date:String):String{
        val list1=txt_date.split(" ")
        val list2=list1[1].split(":")
        val list3= mutableListOf<Int>()
        for(i in list2) list3.add(i.toInt())
       return LocalTime.of(list3[0],list3[1],list3[2]).format(DateTimeFormatter.ofPattern("hh a"))
    }
}