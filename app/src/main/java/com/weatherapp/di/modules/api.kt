package com.weatherapp.di.modules

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import com.weatherapp.extension.onNotReleaseBuild
import com.weatherapp.model.network.ApiRest
import com.weatherapp.model.network.contract.WeatherContract
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val KoinApiModule = module {
    single(definition = { getOkHttpClient() })
    single { getRetrofit(get()) }

    factory { provideWeatherForLocation(get()) }
}

fun provideWeatherForLocation(retrofit: Retrofit): WeatherContract =
    retrofit.create(WeatherContract::class.java)

fun getOkHttpClient(): OkHttpClient =
    with(OkHttpClient.Builder()) {
        followRedirects(false)
        connectTimeout(20, java.util.concurrent.TimeUnit.SECONDS) // default: 10 seconds
        readTimeout(20, java.util.concurrent.TimeUnit.SECONDS) // default: 10 seconds
        writeTimeout(20, java.util.concurrent.TimeUnit.SECONDS) // default: 10 seconds
        addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original: Request = chain.request()
                val originalHttpUrl = original.url

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter(
                        "appid",
                        "5326d7968ac4402faa81121f2e6aaceb"
                    )
                    .build()

                val requestBuilder: Request.Builder = original.newBuilder().url(url)

                val request: Request = requestBuilder.build()
                return chain.proceed(request)
            }

        })
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
        .baseUrl(ApiRest.API_BASE_URL)
        .client(client)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()
            )
        )
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()