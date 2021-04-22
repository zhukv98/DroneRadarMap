package fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import edu.uc.zhukv.droneradarmap.R
import edu.uc.zhukv.droneradarmap.service.TransparentTileOWM
import kotlinx.android.synthetic.main.fragment_home.*


open class HomeFragment : AppCompatActivity() {

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
        val button = findViewById<View>(R.id.imgCurrentFlag)
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this, DetailsFragment::class.java)
                startActivity(intent)
            }

            private fun Intent(
                onClickListener: View.OnClickListener,
                java: Class<DetailsFragment>
            ): Intent? {
                TODO("Not yet implemented")
            }
        })


    }
}