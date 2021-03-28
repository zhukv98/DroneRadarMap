package dao

import dto.CurrentWeather
import retrofit2.Call
import retrofit2.http.GET

interface IRetrofitWeatherDAO {
    @GET("/v1/geocode/{latitude}/{longitude}/forecast/hourly/{12}hour.json")
    fun getGeoCode(): Call<ArrayList<CurrentWeather>>
    fun getAllForecast(): Any
}