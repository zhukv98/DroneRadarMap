package edu.uc.zhukv.droneradarmap.dao

import edu.uc.zhukv.droneradarmap.dto.Forecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface IWeatherDAO {

    @GET("data/2.5/forecast/hourly")
    fun getForecastByGeoCode(@QueryMap() filter: Map<String,String>) : Call<ArrayList<Forecast>>



}