package edu.uc.zhukv.droneradarmap.dto.ForecastAndData

import com.google.gson.annotations.SerializedName


/*
Data class responsible for auto-parsing the wind data we need returned from the API. Value for "description" should be a short description of the current weather, such as "Overcast Clouds", "Broken Clouds", "Light Rain", etc.
 */
data class GeneralWeatherData (
    @SerializedName("description") val description: String,
    )
{
}