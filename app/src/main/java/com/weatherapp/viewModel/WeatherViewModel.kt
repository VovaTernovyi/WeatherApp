package com.weatherapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.weatherapp.model.repository.WeatherRepository
import org.koin.core.KoinComponent

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel(), KoinComponent {

    val latitude = MutableLiveData<Double>()
    val longitude = MutableLiveData<Double>()

    private val refreshWeatherLiveData = SingleLiveEvent<Unit>()

    val downloadAndSaveWeatherLiveData = Transformations.switchMap(refreshWeatherLiveData) {
        repository.downloadAndSaveWeatherForecastForLocation(latitude.value!!, longitude.value!!)
    }

    val weatherLiveData = Transformations.switchMap(refreshWeatherLiveData) {
        repository.getWeatherForLocationFromDb()
    }

    fun refreshWeatherForecast() {
        refreshWeatherLiveData.call()
    }
}