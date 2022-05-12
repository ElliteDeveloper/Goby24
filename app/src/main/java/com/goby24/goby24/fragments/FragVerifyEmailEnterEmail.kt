package com.goby24.goby24.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.goby24.goby24.GlobalConstant
import com.goby24.goby24.MainActivity
import com.goby24.goby24.R
import com.goby24.goby24.globals.ApiInterface
import com.goby24.goby24.globals.RetrofitInstance
import com.goby24.goby24.models.GlobalResponseModel
import com.goby24.goby24.models.SendVerificationEmailModel
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragVerifyEmailEnterEmail: Fragment() {

    private lateinit var mRootView:View
    private lateinit var mEdEmail:EditText

    companion object {
        fun newInstance(): FragVerifyEmailEnterEmail {
            return FragVerifyEmailEnterEmail()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(R.layout.frag_verify_email_enter_email, container, false)
        initView()
        return mRootView
    }

    private fun initView(){
        mEdEmail = mRootView.findViewById(R.id.ed_frag_verify_email_enter_email)

        mRootView.findViewById<View>(R.id.iv_frag_verify_email_enter_email_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        mRootView.findViewById<View>(R.id.tv_frag_verify_email_enter_email_next).setOnClickListener {
            val strEmail:String = mEdEmail.text.toString()
            if (!GlobalConstant.checkEmail(strEmail)) {
                Toast.makeText(requireContext(), resources.getString(R.string.invalid_email), Toast.LENGTH_LONG).show()
                mRootView.findViewById<EditText>(R.id.ed_frag_verify_email_enter_email).requestFocus()
                return@setOnClickListener
            }
            val data = SendVerificationEmailModel()
            data.email = strEmail
            val progressDialog = ProgressDialog(requireActivity())
            progressDialog.show()
            val retIn = RetrofitInstance.getRetrofitInstanceWithAccessToken().create(ApiInterface::class.java)

            retIn.sendVerificationEmail(data).enqueue(object :
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
                    try{
                        val strResponseBody: String = response.body()!!.string()
                        progressDialog.hide()
                        if (response.code() == 200 || response.code() == 201) {
                            if (response.body() != null) {
                                val gson: Gson = Gson()
                                val sendOTPMailResponse: GlobalResponseModel = gson.fromJson(
                                    strResponseBody,
                                    GlobalResponseModel::class.java
                                )
                                if (sendOTPMailResponse.status && "Mail Sent".equals(sendOTPMailResponse.result)){
                                    if (requireActivity() is MainActivity) {
                                        (requireActivity() as MainActivity).addFrag(FragVerifyEmailOTP.newInstance(strEmail), true, true)
                                    }
                                }else{
                                    Toast.makeText(requireActivity(), sendOTPMailResponse.result, Toast.LENGTH_SHORT).show()
                                }
                            }else{
                                Toast.makeText(requireActivity(), R.string.global_failure, Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(requireActivity(), R.string.global_failure, Toast.LENGTH_SHORT).show()
                        }
                    }catch(e:Exception){
                        if (progressDialog.isShowing) progressDialog.hide()
                        Toast.makeText(requireActivity(), R.string.global_failure,Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }
}