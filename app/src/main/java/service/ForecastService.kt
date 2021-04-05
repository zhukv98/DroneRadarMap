package service

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import dao.IRetrofitWeatherDAO
import com.google.android.gms.tasks.Tasks.await
import dao.IWeatherDAO
import dto.Forecast
import edu.uc.zhukv.droneradarmap.MapsActivityCurrentPlace
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

//you'll need to make a method (let's call it fetchForecast) that actually initiates the retrieval of the forecast based on an inputted geocode.
// This is also where you'd call Kevin's gps function to get the user's current geocode.
// Feed the geocode as a parameter to the getForecast method in IWeatherDAO.
// Check out the teacher's plant service class in his plantDiary project to get an idea of what I'm talking about.
// fetchPlants would be our fetchForecast.
// IPlantDAO would be our IRetrofitWeatherDAO, and ILocalPlantDAO would be our IWeatherDAO

//class ForecastService(application: Application) {
//    private val application = application

//    internal suspend fun fetchForecast(forecast: Forecast): MutableLiveData<ArrayList<Forecast>> {
//        //withContext(Dispatcher.IO)
//        var _forecast = MutableLiveData<ArrayList<Forecast>>()
//        val service = RetrofitClientInstance.retrofitInstance?.create(IRetrofitWeatherDAO::class.java)
//        val forecast = async { service?.getAllForecast() }
//
//        updateLocalForecast(forecast.await())
//
//        val call = service?.getAllForecast()
//        call?.enqueue(object : Callback<ArrayList<Forecast>> {
//            override fun onFailure(call: Call<ArrayList<Forecast>>, t: Throwable) {
//                TODO("Not yet implemented")
//                val j = 1 + 1
//                val i = 1 + 1
//            }

//            override fun onResponse(
//                    call: Call<ArrayList<Forecast>>,
//                    response: Response<ArrayList<Forecast>>
//            ) {
//                TODO("Not yet implemented")
//                _forecast.value = response.body()
//            }
//
//        })
//
//        return _forecast
//
//    }
//}
//
//    private fun async(function: () -> Any?): Any {
//        TODO("Not yet implemented")
//    }
//
//    private suspend fun updateLocalForecast(forecast: Any) {
//        TODO("Not yet implemented")
//        var sizeOfForecast = forecast?.size
//        try {
//            var localRetrofitWeatherDAO = getLocalRetrofitWeatherDAO()
//            localRetrofitWeatherDAO.insertAll(forecast!!)
//        } catch (e: Exception) {
//            Log.e(TAG, e.message!!)
//        }
//    }
//
//    fun getLocalRetrofitWeatherDAO() : IWeatherDAO {
////    TODO("Not yet implemented")
////        val db = Room.databaseBuilder(application, MapsActivityCurrentPlace::class.java, "DroneRadar").build()
////        val localIRetrofitWeatherDAO = db.localRetrofitWeatherDAO()
////        return localIRetrofitWeatherDAO
////    }
//
//    internal fun save(forecast: Forecast) {
//        getLocalRetrofitWeatherDAO().save(forecast)
//    }
//
//
//    private fun Any.await(): Any {
//        TODO("Not yet implemented")
//    }
//
//    private fun Any?.enqueue(callback: Callback<java.util.ArrayList<Forecast>>) {
//        TODO("Not yet implemented")
//    }
//


