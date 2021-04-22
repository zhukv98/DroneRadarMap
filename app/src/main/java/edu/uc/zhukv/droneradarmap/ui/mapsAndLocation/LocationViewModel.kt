package edu.uc.zhukv.droneradarmap.ui.mapsAndLocation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import edu.uc.zhukv.droneradarmap.service.ForecastService
import kotlinx.coroutines.launch

class LocationViewModel(application: Application) : AndroidViewModel(application){
    private var _forecastService: ForecastService = ForecastService(application)
    private val locationLiveData = LocationLiveData(application)
    internal fun getLocationLiveData() = locationLiveData


    init {
        fetchForecast("39.1031", "84.5120")
    }

    fun fetchForecast(lat: String, lon: String){
        viewModelScope.launch {
            _forecastService.fetchForecast(lat, lon)
        }
    }

    internal var forecastService: ForecastService
        get(){return _forecastService}
        set(value) {_forecastService = value}



}