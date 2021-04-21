package fragments

import android.location.LocationManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.uc.zhukv.droneradarmap.R
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //enables back button for maps page
        val actionBar = supportActionBar
        actionBar!!.title = "Go-4-Drone"
        actionBar.setDisplayHomeAsUpEnabled(true)

        //enables navigation bar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.mobile_navigation)
        val navController = findNavController(R.id.mobile_navigation)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNavigationView.setupWithNavController(navController)

        //Here Down is stuff needed for Storm's functionality

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
