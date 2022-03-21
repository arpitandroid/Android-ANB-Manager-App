package aatmanirbharbharat.app.atmanirbharops.api

import aatmanirbharbharat.app.atmanirbharops.model.LoginModel
import aatmanirbharbharat.app.atmanirbharops.util.Constant
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @Headers(
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    )
    @FormUrlEncoded
    @POST(Constant.LOGINAPI)
    fun LoginApi(@FieldMap params: Map<String, String>?): Call<LoginModel>?
}