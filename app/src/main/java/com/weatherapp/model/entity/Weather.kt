package com.weatherapp.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Weather(
    @PrimaryKey
    val id: Int
)