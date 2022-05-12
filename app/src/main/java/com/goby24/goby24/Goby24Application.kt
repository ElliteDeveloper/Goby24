package com.goby24.goby24

import android.app.Application
import android.os.StrictMode

class Goby24Application: Application() {
    override fun onCreate() {
        super.onCreate()
        var policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }
}