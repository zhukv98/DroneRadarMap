package edu.uc.zhukv.droneradarmap.dto.ForecastAndData

import com.google.gson.annotations.SerializedName

/*
Data class responsible for Auto-Parsing some aspects of JSON Data from the API (chanceOfRain, cloudCoverage), as well as act as a buffer for other data classes which are responsible for auto-parsing their respective data (temperatureData, generalWeather, and windData)
 */
data class RawForecast(@SerializedName(value = "pop") val chanceOfRain: Long,
                       @SerializedName(value = "clouds") val cloudCoverage: Map<String,Long>,
                       @SerializedName(value = "main") val temperatureData: TemperatureData,
                       @SerializedName(value = "weather") val generalWeather: GeneralWeatherData,
                       @SerializedName(value = "wind") val windData: WindData)
{

}