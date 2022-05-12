package com.goby24.goby24.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.goby24.goby24.R
import com.goby24.goby24.globals.ApiInterface
import com.goby24.goby24.globals.RetrofitInstance
import com.goby24.goby24.globals.SharedPreferenceHelper
import com.goby24.goby24.models.GlobalResponseModel
import com.goby24.goby24.models.SendVerificationMobileOTPModel
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragVerifyPhoneOTP : Fragment() {

    private lateinit var mRootView: View
    private lateinit var mEdOtp1: EditText
    private lateinit var mEdOtp2: EditText
    private lateinit var mEdOtp3: EditText
    private lateinit var mEdOtp4: EditText
    private val mNDuration = 10 * 60 * 1000
    private lateinit var mTvDuration: TextView
    private lateinit var mTimer: CountDownTimer

    companion object {
        private var mStrMobile = ""
        private var mFlagWithPhone = false
        fun newInstance(): FragVerifyPhoneOTP {
            mFlagWithPhone = false
            return FragVerifyPhoneOTP()
        }

        fun newInstance(strMobile: String): FragVerifyPhoneOTP {
            mFlagWithPhone = true
            mStrMobile = strMobile
            return FragVerifyPhoneOTP()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mRootView = inflater.inflate(R.layout.frag_verify_phone_otp, container, false)
        initView()
        return mRootView
    }

    private fun initView() {
        mEdOtp1 = mRootView.findViewById(R.id.ed_frag_verify_phone_otp1)
        mEdOtp2 = mRootView.findViewById(R.id.ed_frag_verify_phone_otp2)
        mEdOtp3 = mRootView.findViewById(R.id.ed_frag_verify_phone_otp3)
        mEdOtp4 = mRootView.findViewById(R.id.ed_frag_verify_phone_otp4)
        mTvDuration = mRootView.findViewById(R.id.tv_frag_verify_phone_otp_time)

        mRootView.findViewById<View>(R.id.iv_frag_verify_phone_otp_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        mEdOtp1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (mEdOtp1.text.length == 1) {
                    mEdOtp2.requestFocus()
                }
            }
        })

        mEdOtp2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (mEdOtp2.text.length == 1) {
                    mEdOtp3.requestFocus()
                }
            }
        })

        mEdOtp3.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (mEdOtp3.text.length == 1) {
                    mEdOtp4.requestFocus()
                }
            }
        })


        mRootView.findViewById<View>(R.id.tv_frag_verify_phone_otp_send).setOnClickListener {
            val strOTP1: String = mEdOtp1.text.toString()
            val strOTP2: String = mEdOtp2.text.toString()
            val strOTP3: String = mEdOtp3.text.toString()
            val strOTP4: String = mEdOtp4.text.toString()
            if (strOTP1.isEmpty()) {
                Toast.makeText(requireActivity(), R.string.enter_valid_otp, Toast.LENGTH_LONG)
                    .show()
                mEdOtp1.requestFocus()
                return@setOnClickListener
            }
            if (strOTP2.isEmpty()) {
                Toast.makeText(requireActivity(), R.string.enter_valid_otp, Toast.LENGTH_LONG)
                    .show()
                mEdOtp2.requestFocus()
                return@setOnClickListener
            }
            if (strOTP3.isEmpty()) {
                Toast.makeText(requireActivity(), R.string.enter_valid_otp, Toast.LENGTH_LONG)
                    .show()
                mEdOtp3.requestFocus()
                return@setOnClickListener
            }
            if (strOTP4.isEmpty()) {
                Toast.makeText(requireActivity(), R.string.enter_valid_otp, Toast.LENGTH_LONG)
                    .show()
                mEdOtp4.requestFocus()
                return@setOnClickListener
            }
            val strOTP: String = strOTP1 + strOTP2 + strOTP3 + strOTP4
            val data = SendVerificationMobileOTPModel()
            data.otp = strOTP
            val progressDialog = ProgressDialog(requireActivity())
            progressDialog.show()

            val retIn = RetrofitInstance.getRetrofitInstanceWithAccessToken()
                .create(ApiInterface::class.java)

            retIn.sendVerificationMobileOTP(data).enqueue(object :
                Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    progressDialog.hide()
                    Toast.makeText(
                        requireActivity(),
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    mTimer.cancel()
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    try {
                        val strResponseBody: String = response.body()!!.string()
                        progressDialog.hide()
                        if (response.code() == 201 || response.code() == 200) {
                            if (response.body() != null) {
                                val gson = Gson()
                                val sendOTPMobileResponse: GlobalResponseModel = gson.fromJson(
                                    strResponseBody,
                                    GlobalResponseModel::class.java
                                )
                                if (sendOTPMobileResponse.status && sendOTPMobileResponse.result.contains("Mobile Number Verified", true)) {
                                    Toast.makeText(requireActivity(), sendOTPMobileResponse.result, Toast.LENGTH_SHORT).show()
                                    SharedPreferenceHelper.setMobileVerifiedStatus(requireActivity().applicationContext, true)
                                    if (mFlagWithPhone) {
                                        requireActivity().onBackPressed()
                                    }
                                    requireActivity().onBackPressed()
                                } else {
                                    Toast.makeText(requireActivity(), sendOTPMobileResponse.result, Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(requireActivity(), R.string.global_failure, Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(requireActivity(), response.message(), Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        if (progressDialog.isShowing) progressDialog.hide()
                        Toast.makeText(requireActivity(), R.string.global_failure, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

        val nMin = (mNDuration / 1000 / 60)
        val nSec = ((mNDuration - nMin * 60 * 1000) / 1000).toInt()
        val strDuration = String.format("00:%02d:%02d", nMin, nSec)
        mTvDuration.text = strDuration

        mTimer = object : CountDownTimer(mNDuration.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTvDuration.run {
                    val nMin = (millisUntilFinished / 1000 / 60).toInt()
                    val nSec = ((millisUntilFinished - nMin * 60 * 1000) / 1000).toInt()
                    val strDuration = String.format("00:%02d:%02d", nMin, nSec)
                    mTvDuration.text = strDuration
                }
            }

            override fun onFinish() {

            }
        }
        mTimer.start()
    }
}