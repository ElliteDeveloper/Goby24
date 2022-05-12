package com.goby24.goby24.globals

import com.goby24.goby24.models.*
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import retrofit2.http.Headers
import java.io.IOException


interface ApiInterface {
    @Headers("Content-Type:application/json")
    @POST("users/register-newuser/")
    fun registerUser(
        @Body info: RegisterUserModel
    ): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("authentication/token/")
    fun signinUser(
        @Body info: SigninUserModel
    ): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("users/send-email/")
    fun sendVerificationEmail(
        @Body info: SendVerificationEmailModel
    ): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("users/verify-email/")
    fun sendVerificationEmailOTP(
        @Body info: SendVerificationEmailOTPModel
    ): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("users/send-otp/")
    fun sendVerificationMobile(
    ): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("users/verify-otp/")
    fun sendVerificationMobileOTP(
        @Body info: SendVerificationMobileOTPModel
    ): retrofit2.Call<ResponseBody>

    //Api for get Basic Traveller Ride History
    @Headers("Content-Type:application/json")
    @GET("ride/traveller_rides/")
    fun getBasicRideHistoryAsTraveller(
        @Query("page") page:Int, @Query("size") page_size:Int
    ): retrofit2.Call<ResponseBody>

    //Api for get Basic Rider Ride History
    @Headers("Content-Type:application/json")
    @GET("ride/rider_rides/")
    fun getBasicRideHistoryAsRider(
        @Query("page") page:Int, @Query("size") size:Int
    ): retrofit2.Call<ResponseBody>

    //Api for get Ride Request History As Traveller
    @Headers("Content-Type:application/json")
    @GET("ride/traveller_ride_request/")
    fun getRideRequestHistoryAsTraveller(
        @Query("page") page:Int, @Query("size") size:Int
    ): retrofit2.Call<ResponseBody>

    //Api for get Ride Request History As Rider
    @Headers("Content-Type:application/json")
    @GET("ride/rider_ride_request/")
    fun getRideRequestHistoryAsRider(
        @Query("page") page:Int, @Query("size") size:Int
    ): retrofit2.Call<ResponseBody>

    //Api for get Tourist Package History As Traveller
    @Headers("Content-Type:application/json")
    @GET("ride/traveller_tourist_package_booking/")
    fun getTouristPackageHistoryAsTraveller(
        @Query("page") page:Int, @Query("size") size:Int
    ): retrofit2.Call<ResponseBody>

    //Api for get Tourist Package History As Rider
    @Headers("Content-Type:application/json")
    @GET("ride/tourist_packages_by_rider/")
    fun getTouristPackageHistoryAsRider(
        @Query("page") page:Int, @Query("size") size:Int
    ): retrofit2.Call<ResponseBody>

    //Api for get Tourist Package Request History As Traveller
    @Headers("Content-Type:application/json")
    @GET("ride/tourist_package_request_for_traveller/")
    fun getTouristPackageRequestHistoryAsTraveller(
        @Query("page") page:Int, @Query("size") size:Int
    ): retrofit2.Call<ResponseBody>

    //Api for get Tourist Package Request History As Rider
    @Headers("Content-Type:application/json")
    @GET("ride/tourist_package_request_for_rider/")
    fun getTouristPackageRequestHistoryAsRider(
        @Query("page") page:Int, @Query("size") size:Int
    ): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @GET("users/new-user/")
    fun getProfile(
    ): retrofit2.Call<ResponseBody>

    //Api for get Tourist Package Offers History
    @Headers("Content-Type:application/json")
    @GET("ride/tourist_packages/")
    fun getTouristPackageOffers(
        @Query("page") page:Int, @Query("size") page_size:Int
    ): retrofit2.Call<ResponseBody>

    //Api for get Tourist Spot History
    @Headers("Content-Type:application/json")
    @GET("ride/tourist-spot-request/")
    fun getTouristPackageAllRequestsFromTourists(
        @Query("page") page:Int, @Query("size") page_size:Int
    ): retrofit2.Call<ResponseBody>
}

class AccessTokenInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        //rewrite the request to add bearer token
        val newRequest: Request = chain.request().newBuilder()
            .header("Authorization", "Bearer " + RetrofitInstance.ACCESS_TOKEN)
            .build()
        return chain.proceed(newRequest)
    }
}

class RefreshTokenInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        //rewrite the request to add bearer token
        val newRequest: Request = chain.request().newBuilder()
            .header("Authorization", "Bearer " + RetrofitInstance.REFRESH_TOKEN)
            .build()
        return chain.proceed(newRequest)
    }
}

class RetrofitInstance {
    companion object {
        lateinit var ACCESS_TOKEN: String
        lateinit var REFRESH_TOKEN: String

        private const val BASE_URL: String = "https://dev.api.goby24.ch/api/"
//        private const val BASE_URL: String = "https://api.goby24.ch/api/"

        private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        private val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        private val clientWithAccessToken: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
            this.addInterceptor(AccessTokenInterceptor())
        }.build()

        private val clientWithRefreshToken: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
            this.addInterceptor(RefreshTokenInterceptor())
        }.build()

        private val clientWithAccessTokenAndWithoutBody: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(AccessTokenInterceptor())
        }.build()

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getRetrofitInstanceWithAccessToken(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientWithAccessToken)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getRetrofitInstanceWithAccessTokenAndWithoutBody(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientWithAccessTokenAndWithoutBody)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getRetrofitInstanceWithRefreshToken(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientWithRefreshToken)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}