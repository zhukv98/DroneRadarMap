package service

import android.util.Log
import androidx.lifecycle.MutableLiveData
import dao.AirportDAO
import dto.Airport
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AirportService {
    fun fetchAirports(): MutableLiveData<ArrayList<Airport>> {
        var _airports = MutableLiveData<ArrayList<Airport>>()
        var service = RetrofitClientInstanceMaps.retrofitInstance?.create(AirportDAO::class.java)
        val call = service?.getAllAirports()
        call?.enqueue(object : Callback<ArrayList<Airport>> {
            override fun onResponse(
                call: Call<ArrayList<Airport>>,
                response: Response<ArrayList<Airport>>
            ) {
                Log.d("TAG", "Success")
                _airports.value = response.body()
            }

            override fun onFailure(call: Call<ArrayList<Airport>>, t: Throwable) {
                Log.d("TAG", "Failed", t)
                val j = 1 + 1
                val i = 1 + 1
            }
        })
        return _airports
    }
}