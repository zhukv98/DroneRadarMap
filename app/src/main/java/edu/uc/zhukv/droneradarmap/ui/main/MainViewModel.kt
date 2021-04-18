package edu.uc.zhukv.droneradarmap.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.uc.zhukv.droneradarmap.dto.Airport
import edu.uc.zhukv.droneradarmap.dto.Forecast
import edu.uc.zhukv.droneradarmap.service.ForecastService

class MainViewModel : ViewModel(){
    var fullForecast: MutableLiveData<ArrayList<Forecast>> = MutableLiveData<ArrayList<Forecast>>()
    var forecastService: ForecastService.fetchForecast();
}