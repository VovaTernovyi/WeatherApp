package com.weatherapp.extension

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

fun glideLoadImage(
    context: Context,
    url: String,
    imageView: ImageView,
    @DrawableRes placeholder: Int
) {
    Glide
        .with(context)
        .load(url)
        .placeholder(placeholder)
        .error(placeholder)
        .into(imageView)
}

fun glideLoadImage(
    context: Context,
    @DrawableRes drawable: Int,
    imageView: ImageView,
    @DrawableRes placeholder: Int
) {
    Glide
        .with(context)
        .load(drawable)
        .placeholder(placeholder)
        .error(placeholder)
        .into(imageView)
}