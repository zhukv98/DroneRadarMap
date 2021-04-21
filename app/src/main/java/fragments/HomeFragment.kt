package fragments

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import edu.uc.zhukv.droneradarmap.R
import edu.uc.zhukv.droneradarmap.service.TransparentTileOWM
import kotlinx.android.synthetic.main.fragment_home.*
import main.CurrentWeatherConditions

class HomeFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

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

        //enables current weather flag system details
        imgCurrentFlag.setOnClickListener {
            val intent = Intent(this, CurrentWeatherConditions::class.java)
            startActivity(intent)
        }

    }
}