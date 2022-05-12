package com.goby24.goby24

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.goby24.goby24.globals.RetrofitInstance
import com.goby24.goby24.globals.SharedPreferenceHelper
import com.goby24.goby24.models.UserProfileModel
import com.google.gson.Gson

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(Runnable {
            if (SharedPreferenceHelper.getLoginStatus(applicationContext)){
                val gson = Gson()
                GlobalConstant.globalMyProfile = gson.fromJson(SharedPreferenceHelper.getUserProfileJSONObject(this), UserProfileModel::class.java)
                RetrofitInstance.ACCESS_TOKEN = SharedPreferenceHelper.getAccessToken(this)!!
                RetrofitInstance.REFRESH_TOKEN = SharedPreferenceHelper.getRefreshToken(this)!!
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("startup_page", "dashboard")
                startActivity(intent)
                finish()
            }else {
                val intent = Intent(this, ActivityChooseCountry::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000L)
    }
}