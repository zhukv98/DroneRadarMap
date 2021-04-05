package edu.uc.zhukv.droneradarmap.dto

data class CurrentWeather(
    val forecasts: List<Forecast>,
    val metadata: Metadata
)