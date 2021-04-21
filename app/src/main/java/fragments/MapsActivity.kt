package fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.uc.zhukv.droneradarmap.R

class MapsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        //enable the drop list for the map option
        val adapter = ArrayAdapter.createFromResource(this, R.array.map_options, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }
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

}