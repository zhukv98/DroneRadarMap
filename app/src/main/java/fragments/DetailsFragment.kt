package fragments

import android.app.Activity
import android.app.Service
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import edu.uc.zhukv.droneradarmap.R
import edu.uc.zhukv.droneradarmap.service.FlagSystemService
import edu.uc.zhukv.droneradarmap.ui.mapsAndLocation.LocationViewModel

class DetailsFragment : AppCompatActivity() {

    companion object{
            const val temperature = "Temperature: "
            const val windSpeed = "Wind Speed: "
            const val cloudCoverage = "Cloud Coverage: "
    }

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.details_fragment)



        //private fun requestUpdateLiveData() {
            //localViewModel. geLocationLiveData().observe(Observer)
        //}

    }
}