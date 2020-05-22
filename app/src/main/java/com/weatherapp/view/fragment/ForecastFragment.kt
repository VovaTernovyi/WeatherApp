package com.weatherapp.view.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.location.*
import com.weatherapp.R
import com.weatherapp.databinding.FragmentForecastBinding
import com.weatherapp.extension.onError
import com.weatherapp.model.entity.Current
import com.weatherapp.model.network.ApiRest
import com.weatherapp.view.adapter.WeatherForecastAdapter
import com.weatherapp.viewModel.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_forecast.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ForecastFragment : Fragment() {

    private val permissionId = 44

    private lateinit var binding: FragmentForecastBinding
    val viewModel: WeatherViewModel by viewModel()

    lateinit var fusedLocationClient: FusedLocationProviderClient
    private val weatherForecastAdapter: WeatherForecastAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentForecastBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getLastLocation()
        viewModel.weatherLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.daily?.let {
                    weatherForecastAdapter.clearAndAddWeatherForecast(it)
                }
                showCurrentWeather(it.current)
            }
        })
        setupView()
    }

    override fun onResume() {
        super.onResume()
        if (checkPermissions()) {
            getLastLocation()
        }
        viewModel.refreshWeatherForecast()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionId) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }
        }
    }

    private fun showCurrentWeather(current: Current) {
        binding.apply {
            imageUrl =
                ApiRest.IMAGE_BASE_URL + getString(R.string.image_format, current.weather[0].icon)
            temperature = current.temperature.toString()
            feelsLike = current.feelsLike.toString()
            sunrise = current.sunrise.toLong()
            sunset = current.sunset.toLong()
            pressure = current.pressure.toString()
            humidity = current.humidity.toString()
            wind = current.windSpeed.toString()
        }
    }

    private fun setupView() = binding.apply {
        fragment_forecast_recycler_view.apply {
            adapter = weatherForecastAdapter
        }
        setHasOptionsMenu(true)
        weatherForecastAdapter.onWeatherForecastClickListener = { _ ->

        }
    }

    private fun initDownloadObserver() {
        viewModel.downloadAndSaveWeatherLiveData.observe(viewLifecycleOwner, Observer {
            it.onError { _, _ ->
                Log.e("Download error..... ", it.error?.message ?: "error")
            }
        })
    }

    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation.addOnCompleteListener { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        viewModel.latitude.value = location.latitude
                        viewModel.longitude.value = location.longitude
                        initDownloadObserver()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.apply {
            requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
        }
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation: Location = locationResult.lastLocation
            viewModel.latitude.value = lastLocation.latitude
            viewModel.longitude.value = lastLocation.longitude
            initDownloadObserver()
        }
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

}
