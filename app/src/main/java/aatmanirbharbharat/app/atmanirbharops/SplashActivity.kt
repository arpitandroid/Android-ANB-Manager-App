package aatmanirbharbharat.app.atmanirbharops

import aatmanirbharbharat.app.atmanirbharops.ui.LoginActivity
import aatmanirbharbharat.app.atmanirbharops.ui.MainActivity
import aatmanirbharbharat.app.atmanirbharops.util.Constant
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val secondsDelayed = 1
        Handler().postDelayed({

            if(checkForToken().isNotEmpty()){
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }

        }, (secondsDelayed * 1000).toLong())

    }

    private fun checkForToken(): String {
        val sharedPref = getSharedPreferences(Constant.SHARED_PREF, Context.MODE_PRIVATE)
        return  sharedPref?.getString(Constant.TOKEN, "").toString()
    }
}