package com.weatherapp

import android.app.Application
import com.facebook.stetho.Stetho
import com.weatherapp.di.modules.*
import com.weatherapp.extension.onNotReleaseBuild
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDependencyInjector()
        initMonitoring()
    }

    private fun initDependencyInjector() {
        startKoin {
            androidContext(this@WeatherApplication)
            modules(
                KoinApiModule,
                KoinDatabaseModule,
                KoinRepositoriesModule,
                KoinArchitectureComponentViewModels,
                KoinOtherModule
            )
        }
    }

    private fun initMonitoring() =
        onNotReleaseBuild {
            Stetho.initializeWithDefaults(this)
        }

    companion object {
        lateinit var instance: WeatherApplication
            private set
    }
}