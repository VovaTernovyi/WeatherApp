package com.weatherapp.di.modules

import com.weatherapp.viewModel.WeatherViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val KoinArchitectureComponentViewModels = module {
    viewModel { WeatherViewModel(get()) }
}