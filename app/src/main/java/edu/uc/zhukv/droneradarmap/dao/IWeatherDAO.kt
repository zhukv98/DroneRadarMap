package edu.uc.zhukv.droneradarmap.dao

import edu.uc.zhukv.droneradarmap.dto.ForecastAndData.RawForecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface IWeatherDAO {

    @GET("data/2.5/weather")
    fun getForecastByGeoCode(@QueryMap() filter: Map<String,String>) : Call<RawForecast>



}