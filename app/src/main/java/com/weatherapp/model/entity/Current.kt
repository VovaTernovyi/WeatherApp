package com.weatherapp.model.entity

import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity
data class Current(
    @SerializedName("dt")
    val time: Int,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int,
    @SerializedName("temp")
    val temperature: Int,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("dew_point")
    val dewPoint: Double,
    @SerializedName("uvi")
    val uvi: Double,
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("wind_speed")
    val windSpeed: Int,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("weather")
    @TypeConverters(WeatherListConverter::class)
    val weather: List<Weather>
)