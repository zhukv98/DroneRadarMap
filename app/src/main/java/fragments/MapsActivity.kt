package fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.annotation.NonNull
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import edu.uc.zhukv.droneradarmap.R

class MapsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)


    }




    class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

        private lateinit var mMap: GoogleMap

        private val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        private val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
        private var mLocationPermissionGranted = false
        private val LOCATION_PERMISSION_REQUEST_CODE = 1234
        private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
        private val DEFAULT_ZOOM = 15F

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_maps)

            getLocationPermission()
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            val mapFragment = supportFragmentManager

                .findFragmentById(R.id.map) as SupportMapFragment

            mapFragment.getMapAsync(this)

            //So this is taken from Jeanette's MapsActivity. Not sure if it's necessary, but it doesn't seem to be breaking anything so I'm adding it here just in case
            //enable the drop list for the map option
            val adapter = ArrayAdapter.createFromResource(this, R.array.map_options, android.R.layout.simple_spinner_item)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
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
                        var currentLocation: Location? = task.result
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

            // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
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
    }
    /*
    Keeping Jeanette's implementation just in case
    //map options menu from the menu folder
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.map_options)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.normal_map -> Toast.makeText(this,"Normal Map", Toast.LENGTH_SHORT).show()
            R.id.hybrid_map -> Toast.makeText(this, "Hybrid Map", Toast.LENGTH_SHORT).show()
            R.id.satellite_map -> Toast.makeText(this, "Satellite Map", Toast.LENGTH_SHORT).show()
            R.id.terrain_map-> Toast.makeText(this, "Terrain Map", Toast.LENGTH_SHORT).show()

        }
        return super.onOptionsItemSelected(item)
    }
    */
}