package edu.uc.zhukv.droneradarmap.dao

import edu.uc.zhukv.droneradarmap.dto.Airport
import retrofit2.Call
import retrofit2.http.GET

interface AirportDAO {
    @GET("/yvzzztrk/airportsJSON/master/airports.json")
    fun getAllAirports(): Call<ArrayList<Airport>>
}