package com.weatherapp.extension

import com.weatherapp.BuildConfig
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException

inline fun onNotReleaseBuild(block: () -> Unit) {
    if (BuildConfig.DEBUG) {
        block()
    }
}

inline fun <T> Result<T>.onError(action: (exception: Throwable) -> Unit) {
    this.exceptionOrNull()?.let {
        if (it !is CancellationException && (it as? HttpException)?.code() != 401)
            action(it)
    }
}