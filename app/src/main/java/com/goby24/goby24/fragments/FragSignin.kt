package com.goby24.goby24.fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.goby24.goby24.GlobalConstant
import com.goby24.goby24.LoginActivity
import com.goby24.goby24.MainActivity
import com.goby24.goby24.R
import com.goby24.goby24.globals.ApiInterface
import com.goby24.goby24.globals.RetrofitInstance
import com.goby24.goby24.globals.SharedPreferenceHelper
import com.goby24.goby24.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class FragSignin: Fragment() {

    private lateinit var mRootView:View
    private var mFlagShowPwdStatus = false

    companion object {
        fun newInstance(): FragSignin {
            return FragSignin()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.frag_signin, container, false)
        initView()
        return mRootView;
    }

    @SuppressLint("CutPasteId")
    private fun initView(){
        mRootView.findViewById<View>(R.id.tv_frag_signin_signin).setOnClickListener {
                val strEmail: String = mRootView.findViewById<EditText>(R.id.ed_frag_signin_email).text.toString();
                val strPassword: String = mRootView.findViewById<EditText>(R.id.ed_frag_signin_password).text.toString();
                if (!GlobalConstant.checkEmail(strEmail)) {
                    Toast.makeText(requireContext(), resources.getString(R.string.invalid_email), Toast.LENGTH_LONG).show()
                    mRootView.findViewById<EditText>(R.id.ed_frag_signin_email).requestFocus()
                    return@setOnClickListener
                }

                if (strPassword.isEmpty()) {
                    Toast.makeText(requireContext(),resources.getString(R.string.empty_password),Toast.LENGTH_LONG).show()
                    mRootView.findViewById<EditText>(R.id.ed_frag_signin_password).requestFocus()
                    return@setOnClickListener
                }

                val data = SigninUserModel()
                data.email = strEmail
                data.password = strPassword

                val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
                val progressDialog = ProgressDialog(requireActivity())
                progressDialog.show()
                retIn.signinUser(data).enqueue(object :
                    Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        progressDialog.hide()
                        Toast.makeText(
                            requireActivity(),
                            t.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        try {
                            val strResponseBody: String = response.body()!!.string()
                            if (response.code() == 200 || response.code() == 201) {
                                if (response.body() != null) {
                                    val gson: Gson = Gson()
                                    val signinResponse: SigninResponseModel = gson.fromJson(
                                        strResponseBody,
                                        SigninResponseModel::class.java
                                    )
                                    RetrofitInstance.ACCESS_TOKEN = signinResponse.access
                                    RetrofitInstance.REFRESH_TOKEN = signinResponse.refresh

                                    SharedPreferenceHelper.setRefreshToken(requireActivity(),signinResponse.refresh)
                                    SharedPreferenceHelper.setAccessToken(requireActivity(),signinResponse.access)
                                    getProfile(progressDialog)
                                }
                            } else {
                                if (progressDialog.isShowing) progressDialog.hide()
                                Toast.makeText(requireActivity(),R.string.signin_failed,Toast.LENGTH_SHORT).show()
                            }
                        }catch (e:Exception){
                            if (progressDialog.isShowing) progressDialog.hide()
                            Toast.makeText(requireActivity(), R.string.signin_failed,Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        }

        mRootView.findViewById<View>(R.id.tv_frag_signin_signup).setOnClickListener {
            if (requireActivity() is LoginActivity){
                (requireActivity() as LoginActivity).addFragWithAnimation(FragSignup.newInstance());
            }
        }

        mRootView.findViewById<View>(R.id.tv_frag_signin_forgot_pwd).setOnClickListener {
            if (requireActivity() is LoginActivity){
                (requireActivity() as LoginActivity).addFragWithAnimation(FragForgotPwd.newInstance());
            }
        }

        mRootView.findViewById<View>(R.id.ll_frag_signin_google).setOnClickListener {

        }

        mRootView.findViewById<View>(R.id.ll_frag_signin_fb).setOnClickListener {

        }

        mRootView.findViewById<View>(R.id.ll_frag_signin_apple).setOnClickListener {

        }

        mRootView.findViewById<View>(R.id.iv_frag_signin_show_pwd).setOnClickListener {
            if (mFlagShowPwdStatus) {
                mRootView.findViewById<EditText>(R.id.ed_frag_signin_password).transformationMethod = HideReturnsTransformationMethod.getInstance()
                mRootView.findViewById<ImageView>(R.id.iv_frag_signin_show_pwd).setImageResource(R.drawable.hide_password)
            }else{
                mRootView.findViewById<EditText>(R.id.ed_frag_signin_password).transformationMethod = PasswordTransformationMethod.getInstance()
                mRootView.findViewById<ImageView>(R.id.iv_frag_signin_show_pwd).setImageResource(R.drawable.show_password)
            }
            mFlagShowPwdStatus = !mFlagShowPwdStatus
        }
    }

    private fun getProfile(progressDialog:ProgressDialog){
        val retIn = RetrofitInstance.getRetrofitInstanceWithAccessTokenAndWithoutBody().create(
            ApiInterface::class.java)

        retIn.getProfile().enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                progressDialog.hide()
                Toast.makeText(requireActivity(), t.message, Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try{
                    val strResponseBody: String = response.body()!!.string()
                    progressDialog.hide()
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            var jsonObject: JSONObject = JSONObject(strResponseBody)
                            if (jsonObject.has("result")) {
                                var jsonResult = jsonObject.get("result")
                                val gson = Gson()
                                GlobalConstant.globalMyProfile = gson.fromJson(jsonResult.toString(), UserProfileModel::class.java)
                                SharedPreferenceHelper.setUserProfileJSONObject(requireActivity(), jsonResult.toString())
                            }
                            SharedPreferenceHelper.setLoginStatus(requireActivity(), true)
                            val intent = Intent(requireActivity(), MainActivity::class.java)
                            intent.putExtra("startup_page", "profile")
                            startActivity(intent)
                            requireActivity().finish()
                        }else{
                            Toast.makeText(requireActivity(), R.string.global_failure, Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(requireActivity(), R.string.global_failure, Toast.LENGTH_SHORT).show()
                    }
                }catch(e:Exception){
                    if (progressDialog.isShowing) progressDialog.hide()
                    Toast.makeText(requireActivity(), R.string.global_failure, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}