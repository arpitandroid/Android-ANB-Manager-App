package aatmanirbharbharat.app.atmanirbharops.model

import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("status") val status: Int,
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: Data
)

data class Data(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("mobile") val mobile: String,
    @SerializedName("city") val city: String,
    @SerializedName("token") val token: String,
)
