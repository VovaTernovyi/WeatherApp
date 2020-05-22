package com.weatherapp.di.modules

import com.weatherapp.view.adapter.WeatherForecastAdapter
import org.koin.dsl.module

val KoinOtherModule = module {
    factory { WeatherForecastAdapter() }
}