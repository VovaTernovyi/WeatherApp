package com.weatherapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weatherapp.databinding.ItemWeatherForecastBinding
import com.weatherapp.model.entity.Daily
import com.weatherapp.model.network.ApiRest

class WeatherForecastAdapter :
    RecyclerView.Adapter<WeatherForecastAdapter.WeatherForecastViewHolder>() {

    private val weatherForecastList = mutableListOf<Daily>()
    var onWeatherForecastClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastViewHolder =
        WeatherForecastViewHolder(
            ItemWeatherForecastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount() = weatherForecastList.size

    override fun onBindViewHolder(holder: WeatherForecastViewHolder, position: Int) {
        weatherForecastList[position].let { item ->
            holder.binding.run {
                imageUrl = ApiRest.IMAGE_BASE_URL + item.weather[0].icon + "@2x.png"
                time = item.time.toLong()
                temperatureMin = item.temperature.min.toString()
                temperatureMax = item.temperature.max.toString()
            }
            holder.itemView.setOnClickListener {
                onWeatherForecastClickListener?.invoke(item.time.toString())
            }
        }
    }

    fun clearAndAddWeatherForecast(weatherForecast: List<Daily>) {
        if (weatherForecastList == weatherForecast) {
            return
        }
        weatherForecastList.clear()
        weatherForecastList.addAll(weatherForecast)
        notifyDataSetChanged()
    }

    class WeatherForecastViewHolder(
        val binding: ItemWeatherForecastBinding
    ) : RecyclerView.ViewHolder(binding.root)
}