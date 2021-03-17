package edu.uc.zhukv.droneradarmap.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.uc.zhukv.droneradarmap.dto.Airport
import edu.uc.zhukv.droneradarmap.service.AirportService

class MainViewModel : ViewModel(){

    var airports: MutableLiveData<ArrayList<Airport>> = MutableLiveData()
    var airportService: AirportService = AirportService()
    init{
        fetchAirports()
    }
    fun fetchAirports(){
        airports = airportService.fetchAirports()
    }
}