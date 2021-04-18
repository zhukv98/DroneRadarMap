package edu.uc.zhukv.droneradarmap.dto

import edu.uc.zhukv.droneradarmap.dto.ForecastAndData.RawForecast

data class CurrentWeather(
        val forecasts: List<RawForecast>,
        val metadata: Metadata
)