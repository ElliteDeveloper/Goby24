package com.goby24.goby24.globals

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper {
    companion object {
        private var SH_KEY_REFRESH_TOKEN = "refresh"
        private var SH_KEY_ACCESS_TOKEN = "access"
        private var SH_KEY_FIRST_NAME = "first_name"
        private var SH_KEY_LAST_NAME = "last_name"
        private var SH_KEY_EMAIL = "email"
        private var SH_KEY_EMAIL_VERIFIED = "email_verified"
        private var SH_KEY_MOBILE = "mobile"
        private var SH_KEY_MOBILE_VERIFIED = "mobile_verified"
        private var SH_KEY_DOB = "dob"
        private var SH_KEY_LOGIN_STATUS = "login_status"
        private var SH_KEY_USER_PROFILE = "profile"

        fun setRefreshToken(ctx:Context, strToken:String){
            newInstance(ctx).edit().putString(SH_KEY_REFRESH_TOKEN, strToken).apply()
        }

        fun getRefreshToken(ctx:Context):String?{
            return newInstance(ctx).getString(SH_KEY_REFRESH_TOKEN, "")
        }

        fun setUserProfileJSONObject(ctx:Context, strData:String){
            newInstance(ctx).edit().putString(SH_KEY_USER_PROFILE, strData).apply()
        }

        fun getUserProfileJSONObject(ctx:Context):String?{
            return newInstance(ctx).getString(SH_KEY_USER_PROFILE, "{}")
        }

        fun setAccessToken(ctx:Context, strToken:String){
            newInstance(ctx).edit().putString(SH_KEY_ACCESS_TOKEN, strToken).apply()
        }

        fun getAccessToken(ctx:Context):String?{
            return newInstance(ctx).getString(SH_KEY_ACCESS_TOKEN, "")
        }

        fun newInstance(ctx: Context): SharedPreferences {
            return ctx.getSharedPreferences(ctx.applicationContext.packageName, Context.MODE_PRIVATE)
        }

        fun setFirstName(ctx:Context, strData:String){
            newInstance(ctx).edit().putString(SH_KEY_FIRST_NAME, strData).apply()
        }

        fun getFirstName(ctx:Context):String?{
            return newInstance(ctx).getString(SH_KEY_FIRST_NAME, "")
        }

        fun setLastName(ctx:Context, strData:String){
            newInstance(ctx).edit().putString(SH_KEY_LAST_NAME, strData).apply()
        }

        fun getLastName(ctx:Context):String?{
            return newInstance(ctx).getString(SH_KEY_LAST_NAME, "")
        }

        fun setEmail(ctx:Context, strData:String){
            newInstance(ctx).edit().putString(SH_KEY_EMAIL, strData).apply()
        }

        fun getEmail(ctx:Context):String?{
            return newInstance(ctx).getString(SH_KEY_EMAIL, "")
        }

        fun setEmailVerifiedStatus(ctx:Context, bData:Boolean){
            newInstance(ctx).edit().putBoolean(SH_KEY_EMAIL_VERIFIED, bData).apply()
        }

        fun getEmailVerifiedStatus(ctx:Context): Boolean {
            return newInstance(ctx).getBoolean(SH_KEY_EMAIL_VERIFIED, false)
        }

        fun setMobile(ctx:Context, strData:String){
            newInstance(ctx).edit().putString(SH_KEY_MOBILE, strData).apply()
        }

        fun getMobile(ctx:Context):String?{
            return newInstance(ctx).getString(SH_KEY_MOBILE, "")
        }

        fun setMobileVerifiedStatus(ctx:Context, bData:Boolean){
            newInstance(ctx).edit().putBoolean(SH_KEY_MOBILE_VERIFIED, bData).apply()
        }

        fun getMobileVerifiedStatus(ctx:Context): Boolean {
            return newInstance(ctx).getBoolean(SH_KEY_MOBILE_VERIFIED, false)
        }

        fun setDOB(ctx:Context, strData:String){
            newInstance(ctx).edit().putString(SH_KEY_DOB, strData).apply()
        }

        fun getDOB(ctx:Context):String?{
            return newInstance(ctx).getString(SH_KEY_DOB, "")
        }

        fun setLoginStatus(ctx:Context, bData:Boolean){
            newInstance(ctx).edit().putBoolean(SH_KEY_LOGIN_STATUS, bData).apply()
        }

        fun getLoginStatus(ctx:Context): Boolean {
            return newInstance(ctx).getBoolean(SH_KEY_LOGIN_STATUS, false)
        }

    }
}