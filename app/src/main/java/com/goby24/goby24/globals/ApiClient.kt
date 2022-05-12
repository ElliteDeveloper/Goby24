package com.goby24.goby24.globals

import com.goby24.goby24.models.RegisterUserModel
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException

class ApiClient {
    companion object {
        val BASE_URL = "https://api.goby24.ch/api/"
        val USER_URL = BASE_URL + "users/"
        val USER_REGISTER_URL = USER_URL + "register-newuser/"

        val client: OkHttpClient = OkHttpClient.Builder().build()
        val JSON: MediaType = "application/json; charset=utf-8".toMediaType()

        @Throws(IOException::class)
        fun register_newuser(data:RegisterUserModel): String? {
            val mediaType: MediaType? = JSON
            var gson = Gson()
            var jsonString = gson.toJson(data)
            val body: RequestBody = RequestBody.create(mediaType,
                jsonString
            )
            val request: Request = Request.Builder()
                .url(USER_REGISTER_URL)
                .method("POST", body)
//                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build()
            client.newCall(request).execute().use { response -> return response.body!!.string() }
        }
    }
}