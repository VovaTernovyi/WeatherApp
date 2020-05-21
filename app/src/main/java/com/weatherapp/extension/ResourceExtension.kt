package com.weatherapp.extension

import androidx.lifecycle.LiveData
import com.weatherapp.model.network.Resource

fun <T> Resource<T>.onLoading(action: (T?) -> Unit): Resource<T> {
    if (this.isLoading()) {
        action(this.data)
    }
    return this
}

fun <T> Resource<T>.onSuccess(action: (T) -> Unit): Resource<T> {
    if (this.isSuccess()) {
        action(this.data!!)
    }
    return this
}

fun <T> Resource<T>.onError(action: (data: T?, error: Throwable?) -> Unit): Resource<T> {
    if (this.isError()) {
        action(this.data, this.error)
    }
    return this
}

typealias LiveResource <T> = LiveData<Resource<T>>

typealias LiveResourceList <T> = LiveData<Resource<List<T>>>