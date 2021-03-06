package edu.uc.zhukv.droneradarmap.service


import android.app.Application
import androidx.lifecycle.MutableLiveData
import edu.uc.zhukv.droneradarmap.dao.IWeatherDAO
import edu.uc.zhukv.droneradarmap.dto.ForecastAndData.RawForecast
import edu.uc.zhukv.droneradarmap.dto.ForecastAndData.RefinedForecast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ForecastService(application: Application) {
   private val application = application
    private lateinit var refinedForecast: RefinedForecast

    fun fetchForecast(latitude: String, longitude: String): MutableLiveData<RawForecast>{
        var fullForecast = MutableLiveData<RawForecast>()
        val service = RetrofitClientInstance.retrofitInstance?.create(IWeatherDAO::class.java);
        val filter: Map<String,String> = mapOf<String,String>(
                "lat" to latitude,
                "lon" to longitude,
                "appid" to "df9069ccbd30ae379cf12b83f551c233",
                "units" to "imperial"
        )
        val call = service?.getForecastByGeoCode(filter);
        call?.enqueue(object: Callback<RawForecast>{

            override fun onFailure(call: Call<RawForecast>, t: Throwable) {
                val j = 1 + 1
            }

            override fun onResponse(
                    call: Call<RawForecast>,
                    response: Response<RawForecast>
                ){
                fullForecast.value = response.body();
                }

        })
        return fullForecast;
    }

    /**
     * Refines (aka parses) the raw forecast data into a refinedForecast, which is essentially
     * the rawForecast class but each attribute is in String form.
     *
     * @param rawForecast
     * @return refinedForecast
     */
    fun refineForecast(rawForecast: RawForecast): RefinedForecast{
        var refinedForecast = RefinedForecast()
        refinedForecast.temperature = rawForecast.temperatureData.temperature.toBigDecimal().toPlainString()
        refinedForecast.generalWeather = rawForecast.generalWeather.toString()
        refinedForecast.cloudCoverage = rawForecast.cloudCoverage.getValue("all").toBigDecimal().times(100.toBigDecimal()).toPlainString()
        refinedForecast.windSpeed = rawForecast.windData.speed.toBigDecimal().toPlainString()

        return refinedForecast
    }
}

