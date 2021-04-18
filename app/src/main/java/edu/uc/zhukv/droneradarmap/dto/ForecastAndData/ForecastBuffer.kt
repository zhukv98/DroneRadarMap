package edu.uc.zhukv.droneradarmap.dto.ForecastAndData

import com.google.gson.annotations.SerializedName

/*
Data class that acts as a buffer for the JSON Data received from the API. hourlyForecast is a list of the future hourly forecasts.
 */
data class ForecastBuffer(@SerializedName(value="list") val hourlyForecast: List<Forecast>) {





}