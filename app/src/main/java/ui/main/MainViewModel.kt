package ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dto.Airport
import service.AirportService

class MainViewModel : ViewModel(){

    var airports: MutableLiveData<ArrayList<Airport>> = MutableLiveData()
    var airportService: AirportService = AirportService()
    fun fetchAirports(){
        airports = airportService.fetchAirports()
    }
}