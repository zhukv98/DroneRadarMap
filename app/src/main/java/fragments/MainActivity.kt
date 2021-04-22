package fragments

import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.uc.zhukv.droneradarmap.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        //enables back button for maps page
        val actionBar = supportActionBar
        actionBar!!.title = "Go-4-Drone"
        actionBar.setDisplayHomeAsUpEnabled(true)

        //enables navigation bar
        //val bottomNavigationView = findViewById<BottomNavigationView>(R.id.homepage)
        //val navController = findNavController(R.id.bottom_nav)

        //val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_notifications))
        //setupActionBarWithNavController(navController, appBarConfiguration)

        //bottomNavigationView.setupWithNavController(navController)

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.bottom_nav_menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> Toast.makeText(this,"Home", Toast.LENGTH_SHORT).show()
            R.id.navigation_notifications -> Toast.makeText(this, "Navigation Center", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
        //From here on down I'm not entirely sure what this. It was in the other MainActivity class and it looks relevant to the weather Tile system so I'm keeping it for the moment since that system doesn't seem to be breaking.

        val APP_ID = "c2b2b2a424915a775cacac7d33a2217a"
        val WEATHER_URL = "https://home.openweathermap.org/data/2.5/weather"

        val MIN_TIME: Long = 5000
        val MIN_DISTANCE = 1000f
        val REQUEST_CODE = 101

        val location_provider = LocationManager.GPS_PROVIDER

        val NameOfCity: TextView? = null
        var weatherState:TextView? = null
        var Temperature:TextView? = null
        val weatherIcon: ImageView? = null

        val mCityFinder: RelativeLayout? = null

        val mLocationManager: LocationManager? = null
        val mLocationListener: LocationManager? = null


    }



}

fun MenuInflater.inflate(bottomNavMenu: Int) {

}
