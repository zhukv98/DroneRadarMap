package edu.uc.zhukv.droneradarmap

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dji.common.error.DJIError
import dji.common.error.DJISDKError
import dji.common.flightcontroller.flyzone.FlyZoneCategory
import dji.common.flightcontroller.flyzone.FlyZoneInformation
import dji.common.flightcontroller.flyzone.SubFlyZoneShape
import dji.common.model.LocationCoordinate2D
import dji.common.util.CommonCallbacks.CompletionCallbackWith
import dji.sdk.base.BaseComponent
import dji.sdk.base.BaseProduct
import dji.sdk.base.BaseProduct.ComponentKey
import dji.sdk.sdkmanager.DJISDKInitEvent
import dji.sdk.sdkmanager.DJISDKManager
import edu.uc.zhukv.droneradarmap.ui.main.MainViewModel
import java.util.concurrent.atomic.AtomicBoolean


class MapsActivity : AppCompatActivity(),  OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    private val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    private var mLocationPermissionGranted = false
    private val LOCATION_PERMISSION_REQUEST_CODE = 1234
    private val REQUEST_PERMISSION_CODE = 12345
    private var Restriction_Level = 1
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private val DEFAULT_ZOOM = 15F
    lateinit var mvm: MainViewModel
    private var GEOFENCE_RADIUS = 500F
    private val painter: FlyfrbBasePainter = FlyfrbBasePainter()
    private val missingPermission: List<String> = ArrayList()
    private val isRegistrationInProgress: AtomicBoolean = AtomicBoolean(false)
    private var mHandler: Handler? = null
    private var mProduct: BaseProduct? = null
    val FLAG_CONNECTION_CHANGE = "dji_sdk_connection_change"
    private val REQUIRED_PERMISSION_LIST = arrayOf(
            Manifest.permission.VIBRATE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndRequestPermissions();
        setContentView(R.layout.activity_maps)
        DJISDKManager.getInstance().flyZoneManager
                .setFlyZoneStateCallback { status -> showToast(status.name) }
        getLocationPermission()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mHandler = Handler(Looper.getMainLooper())

    }

    private fun getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (mLocationPermissionGranted) {
            if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            val location = mFusedLocationProviderClient.lastLocation
            location.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var currentLocation:Location? = task.result
                    if (currentLocation != null) {
                        moveCamera(
                                LatLng(currentLocation.latitude, currentLocation.longitude),
                                DEFAULT_ZOOM
                        )
                    }
                }
            }
        }
    }

    private fun moveCamera(latLng: LatLng, zoom: Float) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    private fun getLocationPermission() {
        var permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (ContextCompat.checkSelfPermission(
                        this.applicationContext,
                        FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (ContextCompat.checkSelfPermission(
                            this.applicationContext,
                            COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
            ) {
                mLocationPermissionGranted = true
                val mapFragment = supportFragmentManager
                        .findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)
            } else {
                ActivityCompat.requestPermissions(
                        this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }
    private fun checkAndRequestPermissions() {
        // Check for permissions
        for (eachPermission in REQUIRED_PERMISSION_LIST) {
            if (ContextCompat.checkSelfPermission(this, eachPermission) != PackageManager.PERMISSION_GRANTED) {
                (missingPermission as MutableList<String>).add(eachPermission)
            }
        }
        // Request for missing permissions
        ActivityCompat.requestPermissions(this, missingPermission.toTypedArray(), REQUEST_PERMISSION_CODE)

    }
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray)
    {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    for (permission in grantResults) {
                        if (permission != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false
                            return
                        }
                    }
                    mLocationPermissionGranted = true
                    val mapFragment = supportFragmentManager
                            .findFragmentById(R.id.map) as SupportMapFragment
                    mapFragment.getMapAsync(this)
                }
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (mLocationPermissionGranted) {
            getDeviceLocation()
            if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            mMap.isMyLocationEnabled = true;
            populateAirports()
            AirportMarkers()
            printSurroundFlyZones()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.map_options, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Change the map type based on the user's selection.
        return when (item.itemId) {
            R.id.normal_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                true
            }
            R.id.hybrid_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                true
            }
            R.id.satellite_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                true
            }
            R.id.terrain_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun populateAirports() {
        mvm = MainViewModel()
    }
   private fun AirportMarkers(){
       mvm.fetchAirports()
       mvm.airports.observe(this, Observer {
           it.forEach {
               //Create MarkerOptions object
               if (it.Iata != "") { // Filter out any location that are not airports
                   var position = LatLng(it.Latitude.toDouble(), it.Longitude.toDouble())
                   val markerOptions = MarkerOptions()
                   markerOptions.position(position)
                   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.airport))
                   mMap.addMarker(markerOptions)
                   // addCircle(position, GEOFENCE_RADIUS)
               }
           }
       })
    }
    private fun addCircle(latLng: LatLng, radius: Float){
        var circleOptions = CircleOptions()
        circleOptions.center(latLng)
        circleOptions.radius(radius.toDouble())
        circleOptions.strokeColor(Color.argb(255, 255, 0, 0))
        circleOptions.fillColor(Color.argb(64, 255, 0, 0))
        circleOptions.strokeWidth(4F)
        mMap.addCircle(circleOptions)
    }
    private fun updateFlyZonesOnTheMap(flyZones: ArrayList<FlyZoneInformation>) {
        this@MapsActivity.runOnUiThread {
            mMap.clear()
            //Create MarkerOptions object
            AirportMarkers()
            for (flyZone in flyZones) {
                //print polygon
                if (flyZone.subFlyZones != null) {
                    val polygonItems = flyZone.subFlyZones
                    val itemSize = polygonItems.size
                    for (i in 0 until itemSize) {
                        if (polygonItems[i].shape == SubFlyZoneShape.POLYGON) {
                            addPolygonMarker(polygonItems[i].vertices, flyZone.category, polygonItems[i].maxFlightHeight)
                        } else if (polygonItems[i].shape == SubFlyZoneShape.CYLINDER) {
                            val tmpPos: LocationCoordinate2D = polygonItems[i].center
                            val subRadius = polygonItems[i].radius
                            val circle = CircleOptions()
                            circle.radius(subRadius)
                            circle.center(LatLng(tmpPos.latitude,
                                    tmpPos.longitude))
                            when (flyZone.category) {
                                FlyZoneCategory.WARNING -> {
                                    circle.strokeColor(Color.GREEN)
                                    Restriction_Level = 2
                                }
                                FlyZoneCategory.ENHANCED_WARNING -> {
                                    circle.strokeColor(Color.BLUE)
                                    Restriction_Level = 3
                                }
                                FlyZoneCategory.AUTHORIZATION -> {
                                    circle.strokeColor(Color.YELLOW)
                                    Restriction_Level = 4
                                }
                                FlyZoneCategory.RESTRICTED -> {
                                    circle.strokeColor(Color.RED)
                                    Restriction_Level = 5
                                }
                                else -> {
                                    Restriction_Level = 1
                                }
                            }
                            mMap.addCircle(circle)
                        }
                    }
                } else {
                    val circle = CircleOptions()
                    circle.radius(flyZone.radius)
                    circle.center(LatLng(flyZone.coordinate.latitude, flyZone.coordinate.longitude))
                    when (flyZone.category) {
                        FlyZoneCategory.WARNING -> {
                            circle.strokeColor(Color.GREEN)
                            Restriction_Level = 2
                        }
                        FlyZoneCategory.ENHANCED_WARNING -> {
                            circle.strokeColor(Color.BLUE)
                            Restriction_Level = 3
                        }
                        FlyZoneCategory.AUTHORIZATION -> {
                            circle.strokeColor(Color.YELLOW)
                            Restriction_Level = 4
                        }
                        FlyZoneCategory.RESTRICTED -> {
                            circle.strokeColor(Color.RED)
                            Restriction_Level = 5
                        }
                        else -> {
                            Restriction_Level = 1
                        }
                    }
                    mMap.addCircle(circle)
                }
            }
        }
    }

    private fun addPolygonMarker(polygonPoints: List<LocationCoordinate2D>?, flyZoneCategory: FlyZoneCategory, height: Int) {
        if (polygonPoints == null) {
            return
        }
        val points: ArrayList<LatLng> = ArrayList()
        for (point in polygonPoints) {
            points.add(LatLng(point.latitude, point.longitude))
        }
        var fillColor = resources.getColor(R.color.limit_fill)
        if (painter.heightToColor[height] != null) {
            fillColor = painter.heightToColor[height]!!
        } else if (flyZoneCategory == FlyZoneCategory.AUTHORIZATION) {
            fillColor = resources.getColor(R.color.auth_fill)
        } else if (flyZoneCategory == FlyZoneCategory.ENHANCED_WARNING || flyZoneCategory == FlyZoneCategory.WARNING) {
            fillColor = resources.getColor(R.color.gs_home_fill)
        }
        mMap.addPolygon(PolygonOptions().addAll(points)
                .strokeColor(painter.colorTransparent)
                .fillColor(fillColor))
    }

    private fun printSurroundFlyZones() {
        DJISDKManager.getInstance().flyZoneManager.getFlyZonesInSurroundingArea(object : CompletionCallbackWith<ArrayList<FlyZoneInformation>> {
            override fun onSuccess(flyZones: ArrayList<FlyZoneInformation>) {
                updateFlyZonesOnTheMap(flyZones)
            }

            override fun onFailure(error: DJIError) {
                showToast(error.description)
            }
        })
    }
    fun showToast(msg: String?) {
        this@MapsActivity.runOnUiThread { Toast.makeText(this@MapsActivity, msg, Toast.LENGTH_SHORT).show() }
    }
}