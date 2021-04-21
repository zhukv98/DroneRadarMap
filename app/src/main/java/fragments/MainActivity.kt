package fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.uc.zhukv.droneradarmap.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }


}

fun MenuInflater.inflate(bottomNavMenu: Int) {

}
