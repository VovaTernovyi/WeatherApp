package com.weatherapp.di.modules

import com.weatherapp.model.repository.WeatherRepository
import com.weatherapp.model.repository.WeatherRepositoryImpl
import org.koin.dsl.module

val KoinRepositoriesModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl(get(), get()) }
}