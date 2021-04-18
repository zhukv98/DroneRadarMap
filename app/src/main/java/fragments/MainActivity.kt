package fragments

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.uc.zhukv.droneradarmap.MapsActivity
import edu.uc.zhukv.droneradarmap.R
import edu.uc.zhukv.droneradarmap.Weather_Layer.TransparentTileOWM
import kotlinx.android.synthetic.main.fragment_home.*
import main.CurrentWeatherConditions

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home, R.layout.activity_main, R.layout.activity_maps, R.layout.details_fragment, R.layout.fragment_notifications,
                R.layout.main_fragment)

        //enables back button for maps page
        val actionBar = supportActionBar
        actionBar!!.title = "Maps Activity"
        actionBar.setDisplayHomeAsUpEnabled(true)

        //enables navigation bar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.mobile_navigation)
        val navController = findNavController(R.id.mobile_navigation)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNavigationView.setupWithNavController(navController)

        //enables current weather flag system details
        imgCurrentFlag.setOnClickListener {
            val intent = Intent(this, CurrentWeatherConditions::class.java)
            startActivity(intent)
        }

        //enables see radar button
        btnSeeRadar.setOnClickListener {
            val intent = Intent(this, TransparentTileOWM::class.java)
            startActivity(intent)
        }

        //enables change location button
        btnChangeLocation.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        //enable the drop list for the map option
        val adapter = ArrayAdapter.createFromResource(this, R.array.map_options, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    private fun setContentView(fragmentHome: Int, activityMain: Int, activityMaps: Int, detailsFragment: Int, fragmentNotifications: Int, mainFragment: Int) {

    }


}
