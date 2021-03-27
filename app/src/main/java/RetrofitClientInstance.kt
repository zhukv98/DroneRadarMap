import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientInstance {

    private var retrofit: Retrofit? = null;
    private val BASE_URL = "https://weather.com/swagger-docs/ui/sun/v1/sunV1HourlyForecast.json"

    val retrofitInstance : Retrofit?
        get () {
            if (retrofit == null) {
                retrofit = retrofit2.retrofit.Builder()
                        .baseURL(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit
        }

}