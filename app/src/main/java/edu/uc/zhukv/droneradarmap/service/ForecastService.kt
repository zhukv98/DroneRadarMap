package edu.uc.zhukv.droneradarmap.service


import android.app.Application
import androidx.lifecycle.MutableLiveData
import edu.uc.zhukv.droneradarmap.dao.IWeatherDAO
import edu.uc.zhukv.droneradarmap.dto.ForecastAndData.ForecastBuffer
import edu.uc.zhukv.droneradarmap.dto.ForecastAndData.RawForecast
import edu.uc.zhukv.droneradarmap.dto.ForecastAndData.RefinedForecast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Ref


class ForecastService(application: Application) {
   private val application = application

    fun fetchForecast(latitude: String, longitude: String): MutableLiveData<ForecastBuffer>{
        var fullForecast = MutableLiveData<ForecastBuffer>()
        val service = RetrofitClientInstance.retrofitInstance?.create(IWeatherDAO::class.java);
        val filter: Map<String,String> = mapOf<String,String>(
                "lat" to latitude,
                "lon" to longitude,
                "appid" to "df9069ccbd30ae379cf12b83f551c233",
                "units" to "imperial"
        )
        val call = service?.getForecastByGeoCode(filter);
        call?.enqueue(object: Callback<ForecastBuffer>{

            override fun onFailure(call: Call<ForecastBuffer>, t: Throwable) {
                val j = 1 + 1
            }

            override fun onResponse(
                    call: Call<ForecastBuffer>,
                    response: Response<ForecastBuffer>
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
        val refinedForecast = RefinedForecast()
        refinedForecast.temperature.set(rawForecast.temperatureData.temperature.toBigDecimal().toPlainString())
        refinedForecast.precipitation.set((rawForecast.chanceOfRain.toBigDecimal()*100).toPlainString() +"%")
        refinedForecast.generalWeather.set(rawForecast.generalWeather)
        refinedForecast.cloudCoverage.set(rawForecast.cloudCoverage.getValue("all").toBigDecimal().toPlainString())
        refinedForecast.windSpeed.set(rawForecast.windData.speed.toBigDecimal().toPlainString())


        return refinedForecast()
    }

}

