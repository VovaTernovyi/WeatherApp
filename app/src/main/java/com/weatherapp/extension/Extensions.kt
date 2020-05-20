package com.weatherapp.extension

import com.weatherapp.BuildConfig

inline fun onNotReleaseBuild(block: () -> Unit) {
    if (BuildConfig.DEBUG) {
        block()
    }
}