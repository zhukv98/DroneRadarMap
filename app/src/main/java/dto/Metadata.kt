package dto


import com.google.gson.annotations.SerializedName

data class Metadata(
    @SerializedName("expire_time_gmt")
    val expireTimeGmt: Int,
    val language: String,
    val latitude: Int,
    val longitude: Int,
    @SerializedName("status_code")
    val statusCode: Int,
    val transactionId: String,
    val units: String,
    val version: String
)