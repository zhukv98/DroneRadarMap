package dto

data class CurrentWeather(
    val forecasts: List<Forecast>,
    val metadata: Metadata
)