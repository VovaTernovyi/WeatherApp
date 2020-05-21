package com.weatherapp.model.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity
data class WeatherResponse(
    @PrimaryKey
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Int,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int,
    @SerializedName("current")
    @Embedded
    val current: Current,
    @SerializedName("hourly")
    @TypeConverters(HourlyListConverter::class)
    val hourly: List<Hourly>,
    @SerializedName("daily")
    @TypeConverters(DailyListConverter::class)
    val daily: List<Daily>
)