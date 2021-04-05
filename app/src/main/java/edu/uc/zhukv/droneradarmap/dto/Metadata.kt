package edu.uc.zhukv.droneradarmap.dto

import android.app.usage.UsageEvents

data class Metadata(var latitude: String = "", var longitude: String = "", var weatherConditions: String = "",
                    val weather: Int = 0, var forecast: Int = 0) {
    private var _events: ArrayList<UsageEvents.Event> = ArrayList<UsageEvents.Event>()

    var events : ArrayList<UsageEvents.Event>
        @Exclude get() {return _events}
        set(value) {_events = value}

    annotation class Exclude

    override fun toString(): String {
        return "$weather $forecast $weatherConditions $latitude $longitude"
    }

}