package com.weatherapp.model.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity
data class Daily(
    @SerializedName("dt")
    val time: Int,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int,
    @SerializedName("temp")
    @Embedded
    val temperature: Temperature,
    @SerializedName("feels_like")
    @Embedded
    val feelsLike: FeelsLike,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("dew_point")
    val dewPoint: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("weather")
    @TypeConverters(WeatherListConverter::class)
    val weather: List<Weather>,
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("rain")
    val rain: Double,
    @SerializedName("uvi")
    val uvi: Double
)

class DailyListConverter {

    @TypeConverter
    fun fromListOfDaily(list: List<Daily>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toListOfDaily(json: String): List<Daily> {
        val listType = object : TypeToken<List<Daily>>() {}.type
        return Gson().fromJson(json, listType)
    }
}