package com.weatherapp.model.network.contract

import com.weatherapp.model.entity.WeatherResponse
import com.weatherapp.model.network.ApiRest
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherContract {

    @GET(ApiRest.ONE_CALL_FORECAST)
    fun getWeatherForLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric"
    ): Observable<Response<WeatherResponse>>
}