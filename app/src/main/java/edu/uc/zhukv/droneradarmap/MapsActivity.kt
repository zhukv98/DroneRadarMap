package edu.uc.zhukv.droneradarmap

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.NonNullt
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.main_fragment.*
import edu.uc.zhukv.droneradarmap.ui.main.MainViewModel as MainViewModel
import edu.uc.zhukv.droneradarmap.Weather_Layer.TransparentTileOWM
import java.net.MalformedURLException
import java.net.URL

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mSearchText: EditText
    private val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    private val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    private var mLocationPermissionGranted = false
    private val LOCATION_PERMISSION_REQUEST_CODE = 1234
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private val DEFAULT_ZOOM = 15F
    private val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    private val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    private lateinit var marker: Marker
    lateinit var mvm: MainViewModel
    private var GEOFENCE_RADIUS = 500F

    private val OWM_TILE_URL = "http://tile.openweathermap.org/map/%s/%d/%d/%d.png?appid=d6d46d84c231bd013c9f0088629b0eb8"
    private var spinner: Spinner? = null
    private var tileType = "clouds"
    private var tileOver: TileOverlay? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        mSearchText = findViewById(R.id.input_search)
        getLocationPermission()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    }
    private fun init(){
        mSearchText.setOnEditorActionListener{ textView, actionId, keyEvent ->
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction()==KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){
                    geoLocate()
                }
               false
            }

    }
    private fun geoLocate(){
        val searchString = mSearchText.text.toString()
        val geocoder = Geocoder(this@MapsActivity)
        val list: List<Address>
        list = geocoder.getFromLocationName(searchString, 1)
        if(list.isNotEmpty()){
            val address = list[0]
            Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show()
        }
    }

        getLocationPermission()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray)
    {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    for(permission in grantResults){
                        if(permission != PackageManager.PERMISSION_GRANTED){
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


        spinner = findViewById(R.id.tileType)
        val tileName = arrayOf("Clouds", "Temperature", "Precipitations", "Snow", "Rain", "Wind", "Sea level press.")
        val adpt: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, tileName)
        spinner?.adapter = adpt
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                // Check click
                when (position) {
                    0 -> tileType = "clouds"
                    1 -> tileType = "temp"
                    2 -> tileType = "precipitation"
                    3 -> tileType = "snow"
                    4 -> tileType = "rain"
                    5 -> tileType = "wind"
                    6 -> tileType = "pressure"
                }
                tileOver?.remove()
                setUpMap()
            }
        }
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     *
     *
     * This should only be called once and when we are sure that [.mMap] is not null.
     */
    private fun setUpMap() {
        // Add weather tile
        tileOver = mMap.addTileOverlay(TileOverlayOptions().tileProvider(createTileProvider()));
        //tileOver = mMap.addTileOverlay(TileOverlayOptions().tileProvider(createTransparentTileProvider()))
    }

    private fun createTransparentTileProvider(): TileProvider {
        return TransparentTileOWM(tileType)
    }

    private fun createTileProvider(): TileProvider {
        return object : UrlTileProvider(256, 256) {
            override fun getTileUrl(x: Int, y: Int, zoom: Int): URL {
                val fUrl = String.format(OWM_TILE_URL, tileType, zoom, x, y)
                var url: URL? = null
                try {
                    url = URL(fUrl)
                } catch (mfe: MalformedURLException) {
                    mfe.printStackTrace()
                }
                return url!!
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
            mMap.isMyLocationEnabled = true
            init()
            populateAirports()
            AirportMarkers()

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
               if (it.Iata != ""){ // Filter out any location that are not airports
               var position = LatLng(it.Latitude.toDouble(), it.Longitude.toDouble())
               val markerOptions = MarkerOptions()
               markerOptions.position(position)
               markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.airport))
               mMap.addMarker(markerOptions)
               addCircle(position, GEOFENCE_RADIUS)
               }
           }
       })
    }
    private fun addCircle(latLng: LatLng, radius: Float){
        var circleOptions = CircleOptions()
        circleOptions.center(latLng)
        circleOptions.radius(radius.toDouble())
        circleOptions.strokeColor(Color.argb(255,255,0,0))
        circleOptions.fillColor(Color.argb(64,255,0,0))
        circleOptions.strokeWidth(4F)
        mMap.addCircle(circleOptions)
    }
}
