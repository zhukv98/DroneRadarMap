package edu.uc.zhukv.droneradarmap.dto.ForecastAndData

import com.google.gson.annotations.SerializedName


/*
Data class responsible for auto-parsing the wind data we need returned from the API. Value returned for "speed" should be the current wind speed (mph)
 */
class WindData(
    @SerializedName("speed")val speed: Double,
) {

}
