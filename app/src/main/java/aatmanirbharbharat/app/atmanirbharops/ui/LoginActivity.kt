package aatmanirbharbharat.app.atmanirbharops.ui

import aatmanirbharbharat.app.atmanirbharops.R
import aatmanirbharbharat.app.atmanirbharops.`interface`.LoginInterface
import aatmanirbharbharat.app.atmanirbharops.databinding.ActivityLoginBinding
import aatmanirbharbharat.app.atmanirbharops.model.LoginDataCall
import aatmanirbharbharat.app.atmanirbharops.model.LoginModel
import aatmanirbharbharat.app.atmanirbharops.presenter.LoginScreenPresenter
import aatmanirbharbharat.app.atmanirbharops.util.Constant
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi

class LoginActivity : AppCompatActivity(), LoginInterface.View {

    private lateinit var binding: ActivityLoginBinding
    var param = HashMap<String, String>()

    private var presenter: LoginScreenPresenter? = null
    var loginData: LoginModel? = null

    companion object {
        var EMAIL = "username"
        var PASSWORD = "password"
       // var FCM_TOKEN = "fcm_token"
        //        var DEVICE_ID = "device_id"
//        var DEVICE_TYPE = "device_type"
        const val TAG = "LoginFragment"
        lateinit var instance: LoginActivity private set

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        instance = this

        binding.loginButton.setOnClickListener {
            hitApi()
        }
    }

    private fun hitApi() {
        presenter = LoginScreenPresenter(this)
        initParam()
        if(checkLoginParam(param)){
            binding.loginButton.isClickable=false
            binding.progressBar.visibility = View.VISIBLE
            presenter?.getDataFromAPI(param)
        }
    }

    private fun initParam() {
        param[EMAIL] = binding.mobileEditText.text.toString()
        param[PASSWORD] = binding.passwordEditText.text.toString()
    }

    private fun checkLoginParam(param: HashMap<String, String>): Boolean {
        return when {
            param[LoginDataCall.EMAIL].isNullOrEmpty() -> {
                Toast.makeText(this@LoginActivity, "Registered Email Required", Toast.LENGTH_SHORT).show()
                false
            }
            param[LoginDataCall.PASSWORD].isNullOrEmpty() -> {
                Toast.makeText(this@LoginActivity, "Password Field Empty", Toast.LENGTH_SHORT).show()
                false
            }
            else -> {
                true
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onGetDataSuccess() {
        loginData = presenter?.showData()!!
        binding.loginButton.isClickable=true
        binding.progressBar.visibility = View.GONE
        saveSharedPref()
        val intent = Intent(this@LoginActivity,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
        Toast.makeText(this@LoginActivity, "Login Successfull", Toast.LENGTH_SHORT).show()
    }

    override fun onGetDataFailure(message: String) {
        Log.d("Status", message)
        binding.loginButton.isClickable=true
        binding.progressBar.visibility = View.GONE
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun saveSharedPref() {
        val sharedPref = getSharedPreferences(Constant.SHARED_PREF, Context.MODE_PRIVATE)
        with(sharedPref?.edit()) {
            this!!.putString(Constant.TOKEN, loginData?.data?.token)
            apply()
        }
    }

}