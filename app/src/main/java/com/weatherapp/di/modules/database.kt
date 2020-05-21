package com.weatherapp.di.modules

import androidx.room.Room
import com.weatherapp.model.database.WeatherDatabase
import org.koin.dsl.module

val KoinDatabaseModule = module {

    single {
        Room.databaseBuilder(
            get(),
            WeatherDatabase::class.java,
            WeatherDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    factory { get<WeatherDatabase>().weatherDao() }
}