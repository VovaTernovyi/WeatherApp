package com.weatherapp.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.weatherapp.model.entity.DailyListConverter
import com.weatherapp.model.entity.HourlyListConverter
import com.weatherapp.model.entity.WeatherListConverter
import com.weatherapp.model.entity.WeatherResponse

@Database(
    entities = [
        WeatherResponse::class
    ],
    version = WeatherDatabase.DATABASE_VERSION,
    exportSchema = WeatherDatabase.EXPORT_SCHEMA
)
@TypeConverters(WeatherListConverter::class, DailyListConverter::class, HourlyListConverter::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        const val DATABASE_VERSION = 1
        const val EXPORT_SCHEMA = false
        const val DATABASE_NAME = "weather_database"
    }
}