package com.weatherapp.view.custom

import android.text.format.DateFormat
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.weatherapp.R
import com.weatherapp.extension.glideLoadImage
import java.util.*

@BindingAdapter("loadImage")
fun loadImage(img: ImageView, url: String?) {
    if (!url.isNullOrBlank()) {
        glideLoadImage(img.context, url, img, R.drawable.placeholder)
    } else {
        img.setImageResource(R.drawable.placeholder)
    }
}

@BindingAdapter("time")
fun formatTime(textView: TextView, timestamp: Long) {
    val calendar: Calendar = Calendar.getInstance(Locale.getDefault())
    calendar.timeInMillis = timestamp * 1000
    val date: String = DateFormat.format("hh:mm:ss", calendar).toString()

    textView.text = date
}

@BindingAdapter("date")
fun formatDate(textView: TextView, timestamp: Long) {
    val calendar: Calendar = Calendar.getInstance(Locale.getDefault())
    calendar.timeInMillis = timestamp * 1000
    val date: String = DateFormat.format("dd MMMM", calendar).toString()

    textView.text = date
}