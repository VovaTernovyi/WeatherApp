package com.weatherapp.model.network

class Resource<T> private constructor(
    private val status: Status,
    val data: T?,
    val error: Throwable?
) {

    companion object {
        fun <T> success(data: T) = Resource<T>(Status.SUCCESS, data, null)
        fun <T> error(error: Throwable?, data: T? = null) = Resource<T>(Status.ERROR, data, error)
        fun <T> loading(data: T? = null) = Resource<T>(Status.LOADING, data, null)
    }


    fun isError() = status == Status.ERROR
    fun isLoading() = status == Status.LOADING
    fun isSuccess() = status == Status.SUCCESS
}

private enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}