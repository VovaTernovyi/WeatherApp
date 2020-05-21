package com.weatherapp.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weatherapp.model.entity.WeatherResponse

@Dao
abstract class WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertWeather(weather: WeatherResponse)

    @Query("SELECT * FROM WeatherResponse")
    abstract fun loadWeatherLiveData(): LiveData<WeatherResponse>
}