package edu.uc.zhukv.droneradarmap.dao

import edu.uc.zhukv.droneradarmap.dto.Forecast
import retrofit2.Call
import retrofit2.http.GET

//There has to be a video by the professor that shows how to replace those parameters in the GET call of the IRetrofitWeather DAO.
// We think it's in module 5. Find it, watch it, and implement it to replace the longitude and latitude values

interface IWeatherDAO {

    @GET("/v1/geocode/{latitude}/{longitude}/forecast/hourly/{12}hour.json")
    fun getForecastByGeoCode(): Call<ArrayList<Forecast>>
    fun save(forecast: Forecast)
    fun insertAll(forecast: Unit)


}