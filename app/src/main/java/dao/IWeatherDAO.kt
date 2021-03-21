package dao

import dto.CurrentWeather
import retrofit2.Call
import retrofit2.http.GET

interface IWeatherDAO {

    @GET("/v1/geocode/{latitude}/{longitude}/forecast/hourly/{hours}hour.json")
    fun getGeoCode(): Call<ArrayList<CurrentWeather>>

    @GET("/v1/location/{postalCode}/forecast/hourly/{hours}hour.json")
    fun getPostalCode(): Call<ArrayList<CurrentWeather>>


}