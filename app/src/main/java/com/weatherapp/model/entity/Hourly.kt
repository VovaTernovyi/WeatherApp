package com.weatherapp.model.entity

import androidx.room.Entity
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity
data class Hourly(
    @SerializedName("dt")
    val time: Int,
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("dew_point")
    val dewPoint: Double,
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("weather")
    @TypeConverters(WeatherListConverter::class)
    val weather: List<Weather>
)

class HourlyListConverter {

    @TypeConverter
    fun fromListOfHourly(list: List<Hourly>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toListOfHourly(json: String): List<Hourly> {
        val listType = object : TypeToken<List<Hourly>>() {}.type
        return Gson().fromJson(json, listType)
    }
}