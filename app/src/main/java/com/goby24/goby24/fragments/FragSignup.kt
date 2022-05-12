package com.goby24.goby24.fragments

import android.app.DatePickerDialog
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
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.goby24.goby24.GlobalConstant
import com.goby24.goby24.LoginActivity
import com.goby24.goby24.MainActivity
import com.goby24.goby24.R
import com.goby24.goby24.globals.ApiInterface
import com.goby24.goby24.globals.RetrofitInstance
import com.goby24.goby24.globals.SharedPreferenceHelper
import com.goby24.goby24.models.RegisterUserModel
import com.goby24.goby24.models.SigninResponseModel
import com.goby24.goby24.models.SignupResponseModel
import com.google.gson.Gson
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import okhttp3.ResponseBody
import java.util.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class FragSignup: Fragment() {

    private lateinit var mRootView:View
    private lateinit var mCcpSignup:CountryCodePicker
    private lateinit var mEdPhone:EditText
    private var mFlagShowPwdStatus = false

    companion object {
        fun newInstance(): FragSignup {
            return FragSignup()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.frag_signup, container, false)
        initView()
        return mRootView;
    }

    private fun initView(){
        mCcpSignup = mRootView.findViewById(R.id.ccp_frag_signup)
        mEdPhone = mRootView.findViewById(R.id.ed_frag_signup_phone)
        mCcpSignup.registerPhoneNumberTextView(mEdPhone)

        mRootView.findViewById<View>(R.id.tv_frag_signup_signin).setOnClickListener {
            if (requireActivity() is LoginActivity){
                (requireActivity() as LoginActivity).addFragWithAnimation(FragSignin.newInstance());
            }
        }

        mRootView.findViewById<View>(R.id.iv_frag_signup_show_pwd).setOnClickListener {
            if (mFlagShowPwdStatus) {
                mRootView.findViewById<EditText>(R.id.ed_frag_signup_password).transformationMethod = HideReturnsTransformationMethod.getInstance()
                mRootView.findViewById<ImageView>(R.id.iv_frag_signup_show_pwd).setImageResource(R.drawable.hide_password)
            }else{
                mRootView.findViewById<EditText>(R.id.ed_frag_signup_password).transformationMethod = PasswordTransformationMethod.getInstance()
                mRootView.findViewById<ImageView>(R.id.iv_frag_signup_show_pwd).setImageResource(R.drawable.show_password)
            }
            mFlagShowPwdStatus = !mFlagShowPwdStatus
        }

        mRootView.findViewById<View>(R.id.ed_frag_signup_birthday).setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(requireActivity(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                mRootView.findViewById<TextView>(R.id.ed_frag_signup_birthday).text = String.format("%04d-%02d-%02d", year, monthOfYear+1, dayOfMonth)

            }, year, month, day)

            dpd.show()
        }

        mRootView.findViewById<View>(R.id.tv_frag_signup_signup).setOnClickListener {
            val strFirstName = mRootView.findViewById<EditText>(R.id.ed_frag_signup_first_name).text.toString()
            val strLastName = mRootView.findViewById<EditText>(R.id.ed_frag_signup_last_name).text.toString()
            val strEmail = mRootView.findViewById<EditText>(R.id.ed_frag_signup_email).text.toString()
            val strPassword = mRootView.findViewById<EditText>(R.id.ed_frag_signup_password).text.toString()
            val strMobile = mCcpSignup.selectedCountryCodeWithPlus + mEdPhone.text.toString()
            val strDob = mRootView.findViewById<TextView>(R.id.ed_frag_signup_birthday).text.toString()

            if (strFirstName.isEmpty()) {
                Toast.makeText(requireContext(),resources.getString(R.string.empty_firstname), Toast.LENGTH_LONG).show()
                mRootView.findViewById<EditText>(R.id.ed_frag_signup_first_name).requestFocus()
                return@setOnClickListener
            }

            if (strLastName.isEmpty()) {
                Toast.makeText(requireContext(),resources.getString(R.string.empty_lastname), Toast.LENGTH_LONG).show()
                mRootView.findViewById<EditText>(R.id.ed_frag_signup_last_name).requestFocus()
                return@setOnClickListener
            }

            if (!mCcpSignup.isValid) {
                Toast.makeText(requireContext(),resources.getString(R.string.empty_mobile), Toast.LENGTH_LONG).show()
                mRootView.findViewById<EditText>(R.id.ed_frag_signup_phone).requestFocus()
                return@setOnClickListener
            }

            if (strDob.isEmpty()) {
                Toast.makeText(requireContext(),resources.getString(R.string.empty_dob), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (strEmail.isEmpty() || !GlobalConstant.checkEmail(strEmail)) {
                Toast.makeText(requireContext(), resources.getString(R.string.invalid_email), Toast.LENGTH_LONG).show()
                mRootView.findViewById<EditText>(R.id.ed_frag_signup_email).requestFocus()
                return@setOnClickListener
            }

            if (strPassword.isEmpty()) {
                Toast.makeText(requireContext(),resources.getString(R.string.empty_password), Toast.LENGTH_LONG).show()
                mRootView.findViewById<EditText>(R.id.ed_frag_signup_password).requestFocus()
                return@setOnClickListener
            }

            val data = RegisterUserModel()
            data.first_name = strFirstName
            data.last_name = strLastName
            data.mobile_no = strMobile
            data.dob = strDob
            data.email = strEmail
            data.password = strPassword
            val progressDialog = ProgressDialog(requireActivity())
            progressDialog.show()

            val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

            retIn.registerUser(data).enqueue(object :
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
                        progressDialog.hide()
                        if (response.code() == 201) {
                            if (response.body() != null) {
                                val gson = Gson()
                                val signupResponse: SignupResponseModel = gson.fromJson(
                                    strResponseBody,
                                    SignupResponseModel::class.java
                                )
                                RetrofitInstance.ACCESS_TOKEN = signupResponse.result.access
                                RetrofitInstance.REFRESH_TOKEN = signupResponse.result.refresh

                                SharedPreferenceHelper.setRefreshToken(
                                    requireActivity(),
                                    signupResponse.result.refresh
                                )
                                SharedPreferenceHelper.setAccessToken(
                                    requireActivity(),
                                    signupResponse.result.access
                                )
                            }
                            SharedPreferenceHelper.setFirstName(requireActivity(), strFirstName)
                            SharedPreferenceHelper.setLastName(requireActivity(), strLastName)
                            SharedPreferenceHelper.setEmail(requireActivity(), strEmail)
                            SharedPreferenceHelper.setEmailVerifiedStatus(requireActivity(), false)
                            SharedPreferenceHelper.setMobile(requireActivity(), strMobile)
                            SharedPreferenceHelper.setMobileVerifiedStatus(requireActivity(), false)
                            SharedPreferenceHelper.setDOB(requireActivity(), strDob)
                            SharedPreferenceHelper.setLoginStatus(requireActivity(), true)

                            val intent = Intent(requireActivity(), MainActivity::class.java)
                            intent.putExtra("startup_page", "dashboard")
                            startActivity(intent)
                            requireActivity().finish()
                        } else {
                            Toast.makeText(
                                requireActivity(),
                                R.string.signup_failed,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }catch(e:Exception){
                        if (progressDialog.isShowing) progressDialog.hide()
                        Toast.makeText(
                            requireActivity(),
                            R.string.signup_failed,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }
    }
}