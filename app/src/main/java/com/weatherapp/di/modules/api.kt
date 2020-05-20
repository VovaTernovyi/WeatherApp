package com.weatherapp.di.modules

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import com.weatherapp.BuildConfig
import com.weatherapp.extension.onNotReleaseBuild
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val KoinApiModule = module {
    single(definition = { getOkHttpClient() })
    single { getRetrofit(get()) }
}

fun getOkHttpClient(): OkHttpClient =
    with(OkHttpClient.Builder()) {
        followRedirects(false)
        connectTimeout(20, java.util.concurrent.TimeUnit.SECONDS) // default: 10 seconds
        readTimeout(20, java.util.concurrent.TimeUnit.SECONDS) // default: 10 seconds
        writeTimeout(20, java.util.concurrent.TimeUnit.SECONDS) // default: 10 seconds
        onNotReleaseBuild {
            networkInterceptors().add(StethoInterceptor())
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return build()
    }

fun getRetrofit(client: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(client)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()
            )
        )
        .build()