package dto


import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    val forecasts: List<Forecast>,
    val metadata: Metadata
)