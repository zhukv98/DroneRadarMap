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
import edu.uc.zhukv.droneradarmap.R
import kotlinx.android.synthetic.main.fragment_home.*
import main.CurrentWeatherConditions

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

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNavigationView.setupWithNavController(navController)


    }


}
