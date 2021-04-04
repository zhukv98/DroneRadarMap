package edu.uc.zhukv.droneradarmap.service

import android.util.Log
import androidx.lifecycle.MutableLiveData
import edu.uc.zhukv.droneradarmap.RetrofitClientInstance
import edu.uc.zhukv.droneradarmap.dao.AirportDAO
import edu.uc.zhukv.droneradarmap.dto.Airport
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AirportService {
    internal fun fetchAirports(): MutableLiveData<ArrayList<Airport>> {
        var _airports = MutableLiveData<ArrayList<Airport>>()
        var service = RetrofitClientInstance.retrofitInstance?.create(AirportDAO::class.java)
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