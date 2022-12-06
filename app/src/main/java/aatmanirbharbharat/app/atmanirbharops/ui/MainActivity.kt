package aatmanirbharbharat.app.atmanirbharops.ui

import aatmanirbharbharat.app.atmanirbharops.databinding.ActivityMainBinding
import aatmanirbharbharat.app.atmanirbharops.util.Constant
import aatmanirbharbharat.app.atmanirbharops.util.Constant.Companion.BASE_URL
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private var token = ""

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        checkForToken()

        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled=true
        loadUrl()

    }

    private fun loadUrl(){
      //  binding.webView.loadUrl("https://assleit.com/atmanirbar_bharat/manager/home?token=$token")
        binding.webView.loadUrl(BASE_URL+"manager/home?token=$token")
    }

    inner class WebViewClient : android.webkit.WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (url.startsWith("tel:")) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
                return true
            }else if(url.startsWith("whatsapp:")){
                if(!appInstalledOrNot("com.whatsapp")){
                    // Toast message not installed.
                    Toast.makeText(this@MainActivity, "whatsapp not installed in your device", Toast.LENGTH_SHORT).show()
                }else{
                    // Toast message installed.
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                }
                return true
            }
            return false

        }

        override fun onPageFinished(view: WebView?, url: String) {
            super.onPageFinished(view, url)
            binding.progressBar5.visibility = View.GONE

           // if (url.contains("https://assleit.com/atmanirbar_bharat/manager/home/logout")){
            if (url.contains(BASE_URL+"manager/home/logout")){
                showAlertDialog()
         //   }else if(url.contains("https://assleit.com/atmanirbar_bharat/manager/login")){
            }else if(url.contains(BASE_URL+"manager/login")){
                Toast.makeText(this@MainActivity, "Login Expired Please Login Again", Toast.LENGTH_LONG).show()
                onCleard()
            }
//                else{
//                    Toast.makeText(this@MainActivity, url, Toast.LENGTH_LONG).show()
//                }


        }
    }


    private fun appInstalledOrNot(uri: String): Boolean {
        val pm = packageManager
        return try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }


    private fun checkForToken(){
        val sharedPref = getSharedPreferences(Constant.SHARED_PREF, Context.MODE_PRIVATE)
        token = sharedPref?.getString(Constant.TOKEN, "").toString()
        Log.d("token",token)
    }



//    override fun onBackPressed() {
//        finishAffinity()
//        onBackPressed()
//    }

    private fun onCleard(){
        //clear shared prefrences
        getSharedPreferences(Constant.SHARED_PREF, 0)?.edit()?.clear()?.apply()

        val intent = Intent(this@MainActivity,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure?")
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton(android.R.string.yes) { _, _ ->
            onCleard()
        }



        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(
                this@MainActivity,
                android.R.string.no, Toast.LENGTH_SHORT
            ).show()

            loadUrl()
        }

        builder.show()
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit!")
                .setMessage("Are you sure you want to close?")
                .setPositiveButton("Yes") { dialog, which -> finish() }
                .setNegativeButton("No", null)
                .show()
        }
    }
}