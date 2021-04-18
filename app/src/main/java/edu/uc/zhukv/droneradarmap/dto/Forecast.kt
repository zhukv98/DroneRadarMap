package edu.uc.zhukv.droneradarmap.dto

//filter out what is not needed and see if it is necessary

data class Forecast(val temperature: Int, var dayOfWeek: String, val precipitation: Int, val relativeHumidity: Int, val windSpeed: Int,
                    val windGust: Int, var timeOfDay: String, val windShield: Int, val heatIndex: Int)
{


}