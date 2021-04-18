package edu.uc.zhukv.droneradarmap.dto

import edu.uc.zhukv.droneradarmap.dto.ForecastAndData.Forecast

data class CurrentWeather(
        val forecasts: List<Forecast>,
        val metadata: Metadata
)