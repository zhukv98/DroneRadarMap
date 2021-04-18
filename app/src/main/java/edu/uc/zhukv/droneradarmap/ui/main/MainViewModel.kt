package edu.uc.zhukv.droneradarmap.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.uc.zhukv.droneradarmap.dto.ForecastAndData.Forecast
import edu.uc.zhukv.droneradarmap.dto.LocationDetails
import edu.uc.zhukv.droneradarmap.service.ForecastService

class MainViewModel : ViewModel() {
    var fullForecast: MutableLiveData<ArrayList<Forecast>> = MutableLiveData<ArrayList<Forecast>>();
    var forecastService: ForecastService = ForecastService()

    init{
        fetchForecast(LocationDetails.latitude)
    }


    fun fetchForecast(latitude: String, longitude: String){
        fullForecast = forecastService.fetchForecast(latitude, longitude)
    }

}