package edu.uc.zhukv.droneradarmap.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.google.android.gms.location.FusedLocationProviderClient
import edu.uc.zhukv.droneradarmap.R
import edu.uc.zhukv.droneradarmap.service.FlagSystemService
import edu.uc.zhukv.droneradarmap.ui.mapsAndLocation.LocationViewModel

class MainFragment : Fragment() {

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
    private var flagService: FlagSystemService = FlagSystemService()

    companion object {
        fun newInstance() = MainFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        activity.let{
            viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        }

        locationViewModel.forecastService.fetchForecast(currentLat,currentLon).observe(this, Observer {
            forecast -> flagService.calculateCurrentFlag(forecast)
        })

    }

    private fun prepRequestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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



}