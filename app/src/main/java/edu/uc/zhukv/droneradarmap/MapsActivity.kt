package edu.uc.zhukv.droneradarmap

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import edu.uc.zhukv.droneradarmap.Weather_Layer.TransparentTileOWM
import java.net.MalformedURLException
import java.net.URL


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private val OWM_TILE_URL = "http://tile.openweathermap.org/map/%s/%d/%d/%d.png?appid=d6d46d84c231bd013c9f0088629b0eb8"
    private var spinner: Spinner? = null
    private var tileType = "clouds"
    private var tileOver: TileOverlay? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//                .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)

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
  //      setUpMapIfNeeded()
    }

//    override fun onResume() {
//        super.onResume()
//        setUpMapIfNeeded()
//    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call [.setUpMap] once when [.mMap] is not null.
     *
     *
     * If it isn't installed [SupportMapFragment] (and
     * [MapView][com.google.android.gms.maps.MapView]) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     *
     *
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), [.onCreate] may not be called again so we should call this
     * method in [.onResume] to guarantee that it will be called.
     */
    private fun setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            val mapFragment = supportFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }
        // Check if we were successful in obtaining the map.
        else {
            setUpMap()
        }
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

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}