package com.example.weather_app_iti.model.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromList3hoursToString(list: MutableList<List3hours>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToList3hours(list: String): MutableList<List3hours> {
        val listType = object : TypeToken<MutableList<List3hours>>() {}.type
        return Gson().fromJson(list, listType)
    }
    @TypeConverter
    fun fromList5daysToString(list: MutableList<List5days>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToList5days(list: String): MutableList<List5days> {
        val listType = object : TypeToken<MutableList<List5days>>() {}.type
        return Gson().fromJson(list, listType)
    }
}