package com.weatherapp.model.entity

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class FeelsLike(
    @SerializedName("day")
    val day: Double,
    @SerializedName("night")
    val night: Double,
    @SerializedName("eve")
    val evening: Double,
    @SerializedName("morn")
    val morning: Double
)