package dao

import dto.Airport
import retrofit2.Call
import retrofit2.http.GET

interface AirportDAO {
    @GET("/yvzzztrk/airportsJSON/master/airports.json")
    fun getAllAirports(): Call<ArrayList<Airport>>
}