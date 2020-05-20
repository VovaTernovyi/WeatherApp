package com.weatherapp.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weatherapp.model.entity.Weather

@Database(
    entities = [Weather::class],
    version = WeatherDatabase.DATABASE_VERSION,
    exportSchema = WeatherDatabase.EXPORT_SCHEMA
)
abstract class WeatherDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_VERSION = 1
        const val EXPORT_SCHEMA = false
        const val DATABASE_NAME = "weather_database"
    }
}