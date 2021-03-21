package dto


import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("class")
    val classX: String,
    val clds: Int,
    @SerializedName("day_ind")
    val dayInd: String,
    val dewpt: Int,
    val dow: String,
    @SerializedName("expire_time_gmt")
    val expireTimeGmt: Int,
    @SerializedName("fcst_valid")
    val fcstValid: Int,
    @SerializedName("fcst_valid_local")
    val fcstValidLocal: String,
    @SerializedName("feels_like")
    val feelsLike: Int,
    @SerializedName("golf_category")
    val golfCategory: String,
    @SerializedName("golf_index")
    val golfIndex: Int,
    val gust: Int,
    val hi: Int,
    @SerializedName("icon_code")
    val iconCode: Int,
    @SerializedName("icon_extd")
    val iconExtd: Int,
    val mslp: Int,
    val num: Int,
    @SerializedName("phrase_12char")
    val phrase12char: String,
    @SerializedName("phrase_22char")
    val phrase22char: String,
    @SerializedName("phrase_32char")
    val phrase32char: String,
    val pop: Int,
    @SerializedName("precip_type")
    val precipType: String,
    val qpf: Int,
    val rh: Int,
    val severity: Int,
    @SerializedName("snow_qpf")
    val snowQpf: Int,
    @SerializedName("subphrase_pt1")
    val subphrasePt1: String,
    @SerializedName("subphrase_pt2")
    val subphrasePt2: String,
    @SerializedName("subphrase_pt3")
    val subphrasePt3: String,
    val temp: Int,
    @SerializedName("uv_desc")
    val uvDesc: String,
    @SerializedName("uv_index")
    val uvIndex: Int,
    @SerializedName("uv_index_raw")
    val uvIndexRaw: Int,
    @SerializedName("uv_warning")
    val uvWarning: Int,
    val wc: Int,
    val wdir: Int,
    @SerializedName("wdir_cardinal")
    val wdirCardinal: String,
    val wspd: Int
)