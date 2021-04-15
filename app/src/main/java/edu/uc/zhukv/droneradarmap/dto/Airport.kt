package edu.uc.zhukv.droneradarmap.dto

import com.google.gson.annotations.SerializedName

data class Airport(@SerializedName("name") var name: String,@SerializedName("city") var City: String, @SerializedName("country") var Country: String, @SerializedName("iata") var Iata: String, @SerializedName("latitude") var Latitude: String, @SerializedName("longitude")var Longitude: String){
    override fun toString(): String {
        return "Airport: $name City: $City Country: $Country IATA: $Iata  Lat: $Latitude  Lng: $Longitude"
    }
}