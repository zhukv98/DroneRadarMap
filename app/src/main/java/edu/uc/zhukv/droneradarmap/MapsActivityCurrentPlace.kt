package edu.uc.zhukv.droneradarmap

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

//Put it at the top of the file and converted to constants as that is their purpose. Also made public so that they can be accessed.
public const val APP_ID = "c2b2b2a424915a775cacac7d33a2217a"
public const val WEATHER_URL = "https://home.openweathermap.org/data/2.5/weather"

public const val MIN_TIME: Long = 5000
public const val MIN_DISTANCE = 1000f
public const val REQUEST_CODE = 101

class MapsActivityCurrentPlace(var NameOfCity: TextView? = null) {

//Renamed without the underscore
    var locationProvider = LocationManager.GPS_PROVIDER

    var weatherIcon: ImageView? = null

    var mCityFinder: RelativeLayout? = null

    var mLocationManager: LocationManager? = null
    var mLocationListener: LocationManager? = null

    private val applicationContext: Context
        get() {
            TODO()
        }

    private fun getLocationPermission() =/*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            var locationPermissionGranted = true
        } else {
            //Renamed because of underscores
            val requestAccess = 0
            //ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            //PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }


    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        var locationPermissionGranted = false
        val requestAccess = null
        when (requestCode) {
            requestAccess -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }


    private fun updateLocationUI() {
        TODO("Not yet implemented")
    }
}