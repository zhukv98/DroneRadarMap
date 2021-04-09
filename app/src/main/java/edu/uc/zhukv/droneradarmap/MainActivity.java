package edu.uc.zhukv.droneradarmap;

import android.location.LocationManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity {

    final String APP_ID = "c2b2b2a424915a775cacac7d33a2217a";
    final String WEATHER_URL = "https://home.openweathermap.org/data/2.5/weather";

    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 101;

    String location_provider = LocationManager.GPS_PROVIDER;

    TextView NameOfCity,weatherState, Temperature;
    ImageView weatherIcon;

    RelativeLayout mCityFinder;

    LocationManager mLocationManager;
    LocationManager mLocationListener;


}
