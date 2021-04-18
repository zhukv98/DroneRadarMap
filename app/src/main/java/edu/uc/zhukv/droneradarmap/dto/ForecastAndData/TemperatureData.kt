package edu.uc.zhukv.droneradarmap.dto.ForecastAndData

import com.google.gson.annotations.SerializedName

/*
Data class responsible for auto-parsing the temperature data we need returned from the API. Value returned for "temperature" should be the current temperature (fahrenheit)
 */
data class TemperatureData(
    @SerializedName("temp")val temperature: Double,
) {
}