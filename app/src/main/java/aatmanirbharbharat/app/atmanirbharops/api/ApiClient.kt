package aatmanirbharbharat.app.atmanirbharops.api

import aatmanirbharbharat.app.atmanirbharops.util.Constant
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object{
        private var retrofit: Retrofit? = null
        var gson = GsonBuilder()
            .setLenient()
            .create()

        private val okHttpClient =  OkHttpClient.Builder()
            .readTimeout(10,TimeUnit.SECONDS)
            .connectTimeout(10,TimeUnit.SECONDS)
            .build()

        val client: Retrofit
            get() {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .baseUrl(Constant.BASE_URL)
                        .client(okHttpClient)
                        .build()
                }
                return retrofit!!
            }
    }

}