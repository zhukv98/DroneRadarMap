package edu.uc.zhukv.droneradarmap.dto

import com.google.gson.annotations.SerializedName

data class Airport (@SerializedName("city") var city: String, @SerializedName("country")var country: String, @SerializedName("iata") var iata: String, @SerializedName("latitude")var latitude: String, @SerializedName("longitude")var longitude: String){
    override fun toString(): String {
        return "$city $country $iata  Lat: $latitude  Lng: $longitude"
    }
}