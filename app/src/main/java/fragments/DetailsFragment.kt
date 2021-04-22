package fragments

import android.Manifest
import android.app.Activity
import android.app.Service
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import edu.uc.zhukv.droneradarmap.R
import edu.uc.zhukv.droneradarmap.service.FlagSystemService
import edu.uc.zhukv.droneradarmap.ui.main.MainViewModel
import edu.uc.zhukv.droneradarmap.ui.mapsAndLocation.LocationViewModel

class DetailsFragment : AppCompatActivity() {

    private val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    private val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    private var mLocationPermissionGranted = false
    private val LOCATION_PERMISSION_REQUEST_CODE = 1234
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var viewModel: MainViewModel
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var currentLat: String
    private lateinit var currentLon: String

    private var currentFlag: Int = 0
    private lateinit var flagService: FlagSystemService

    companion object{
            const val temperature = "Temperature: "
            const val windSpeed = "Wind Speed: "
            const val cloudCoverage = "Cloud Coverage: "
    }

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.details_fragment)
        setContentView(R.layout.fragment_home)

        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        this.let{
            viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        }

        locationViewModel.forecastService.fetchForecast(currentLat,currentLon).observe(this, Observer {
            var refinedForecast = locationViewModel.forecastService.refineForecast(it)
            flagService = FlagSystemService(refinedForecast)
        })



        //private fun requestUpdateLiveData() {
            //localViewModel. geLocationLiveData().observe(Observer)
        //}

    }
}