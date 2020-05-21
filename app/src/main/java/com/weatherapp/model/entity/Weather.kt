package com.weatherapp.model.entity

import androidx.room.Entity
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity
data class Weather(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)

class WeatherListConverter {

    @TypeConverter
    fun fromListOfWeather(list: List<Weather>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toListOfWeather(json: String): List<Weather> {
        val listType = object : TypeToken<List<Weather>>() {}.type
        return Gson().fromJson(json, listType)
    }
}