package edu.uc.zhukv.droneradarmap.service


import androidx.lifecycle.MutableLiveData
import edu.uc.zhukv.droneradarmap.dao.IWeatherDAO
import edu.uc.zhukv.droneradarmap.dto.ForecastAndData.Forecast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ForecastService() {
   // private val application = application

    fun fetchForecast(latitude: String, longitude: String): MutableLiveData<ArrayList<Forecast>>{
        var fullForecast = MutableLiveData<ArrayList<Forecast>>()
        val service = RetrofitClientInstance.retrofitInstance?.create(IWeatherDAO::class.java);
        val filter: Map<String,String> = mapOf<String,String>(
                "lat" to latitude,
                "lon" to longitude,
                "appid" to "df9069ccbd30ae379cf12b83f551c233",
                "units" to "imperial"
        )
        val call = service?.getForecastByGeoCode(filter);
        call?.enqueue(object: Callback<ArrayList<Forecast>>{

            override fun onFailure(call: Call<ArrayList<Forecast>>, t: Throwable) {
                val j = 1 + 1
            }

            override fun onResponse(
                    call: Call<ArrayList<Forecast>>,
                    response: Response<ArrayList<Forecast>>
                ){
                fullForecast.value = response.body();
                }

        })
        return fullForecast;
    }

}
