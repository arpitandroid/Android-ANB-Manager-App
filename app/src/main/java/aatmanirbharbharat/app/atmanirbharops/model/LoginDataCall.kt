package aatmanirbharbharat.app.atmanirbharops.model

import aatmanirbharbharat.app.atmanirbharops.R
import aatmanirbharbharat.app.atmanirbharops.`interface`.LoginInterface
import aatmanirbharbharat.app.atmanirbharops.api.ApiClient
import aatmanirbharbharat.app.atmanirbharops.api.ApiInterface
import aatmanirbharbharat.app.atmanirbharops.presenter.LoginScreenPresenter
import aatmanirbharbharat.app.atmanirbharops.ui.LoginActivity
import aatmanirbharbharat.app.atmanirbharops.util.NetworkUtil
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginDataCall : LoginInterface.Model {
    var loginData: LoginModel? = null
    private var apiclient: ApiInterface? = null

    init {
        apiclient = ApiClient.client.create(ApiInterface::class.java)
    }

    companion object {
        var EMAIL = "username"
        var PASSWORD = "password"
    }


    override fun getData(): LoginModel = loginData!!

    override fun initRetrofitCall(param: HashMap<String, String>, presenter: LoginScreenPresenter) {
        if (NetworkUtil.isOnline(LoginActivity.instance)) {
//                if (checkLoginParam(param, presenter)) {
                    val call = apiclient?.LoginApi(param)
                    call?.enqueue(object : Callback<LoginModel?> {
                        override fun onResponse(call: Call<LoginModel?>, response: Response<LoginModel?>) {
                            if (response.isSuccessful && response.body()?.status == 200) {
                                loginData = response.body()
                                Log.d("Data", loginData?.status.toString())
                                presenter.onSuccess()
                            }
                            else {
                                presenter.onFailure(response.body()?.message.toString())
                            }
                        }

                        override fun onFailure(call: Call<LoginModel?>, t: Throwable) {
                            Log.d("failure", t.toString())
                            presenter.onFailure(t.toString())
//                presenter.on(t.message)
                        }
                    })
//                }

        } else {
            presenter.onFailure(LoginActivity.instance.getString(R.string.no_internet_connect))
        }
    }


    private fun checkLoginParam(param: HashMap<String, String>, presenter: LoginScreenPresenter): Boolean {
        return when {
            param[EMAIL].isNullOrEmpty() -> {
                presenter.onFailure(LoginActivity.instance.getString(R.string.email_field_empty))
                false
            }
            param[PASSWORD].isNullOrEmpty() -> {
                presenter.onFailure(LoginActivity.instance.getString(R.string.password_field_empty))
                false
            }
            else -> {
                true
            }
        }
    }


}