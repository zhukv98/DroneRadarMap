package fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import edu.uc.zhukv.droneradarmap.R
import edu.uc.zhukv.droneradarmap.service.FlagSystemService
import edu.uc.zhukv.droneradarmap.service.TransparentTileOWM
import edu.uc.zhukv.droneradarmap.ui.main.MainViewModel
import edu.uc.zhukv.droneradarmap.ui.mapsAndLocation.LocationViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.*


open class HomeFragment : AppCompatActivity() {

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

    companion object {
        fun newInstance() = HomeFragment()
    }


    private fun prepRequestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocationUpdates()
        } else {
            val permissionRequest = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permissionRequest, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }


    private fun requestLocationUpdates() {
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
        locationViewModel.getLocationLiveData().observe(this, Observer {
            currentLat = it.latitude
            currentLon = it.longitude
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.fragment_home)

        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        this.let{
            viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        }

        locationViewModel.forecastService.fetchForecast(currentLat,currentLon).observe(this, Observer {
            var refinedForecast = locationViewModel.forecastService.refineForecast(it)
            flagService = FlagSystemService(refinedForecast)
        })

        //enables see radar button
        btnSeeRadar.setOnClickListener {
            val intent = Intent(this, TransparentTileOWM::class.java)
            startActivity(intent)
        }

        //enables change location button
        btnChangeLocation.setOnClickListener {
            ;
        }

        //enables current weather flag system details


    }


            private fun Intent(
                onClickListener: View.OnClickListener,
                java: Class<DetailsFragment>
            ): Intent? {
                TODO("Not yet implemented")
            }
        })


    }
}