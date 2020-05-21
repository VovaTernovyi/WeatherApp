package com.weatherapp.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.weatherapp.extension.LiveResource
import com.weatherapp.extension.onNotReleaseBuild
import com.weatherapp.model.database.WeatherDao
import com.weatherapp.model.entity.WeatherResponse
import com.weatherapp.model.network.contract.WeatherContract
import io.reactivex.rxjava3.schedulers.Schedulers

interface WeatherRepository {

    fun downloadAndSaveWeatherForecastForLocation(
        latitude: Double,
        longitude: Double
    ): LiveResource<Unit>

    fun getWeatherForLocationFromDb(): LiveData<WeatherResponse>
}

class WeatherRepositoryImpl(
    private val contract: WeatherContract,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    override fun downloadAndSaveWeatherForecastForLocation(
        latitude: Double,
        longitude: Double
    ): LiveResource<Unit> = liveData {
        contract.getWeatherForLocation(latitude, longitude)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map {
                it.body()!!
            }
            .subscribe(
                {
                    weatherDao.insertWeather(it)
                    onNotReleaseBuild {
                        Log.e("Final log Response: ", it.toString())
                    }
                },
                {
                    onNotReleaseBuild {
                        Log.e("Error: ", it.message ?: "error")
                    }
                }
            )
    }

    override fun getWeatherForLocationFromDb(): LiveData<WeatherResponse> {
        return weatherDao.loadWeatherLiveData()
    }

}