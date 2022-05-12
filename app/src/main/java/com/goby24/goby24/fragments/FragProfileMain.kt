package com.goby24.goby24.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.goby24.goby24.MainActivity
import com.goby24.goby24.R
import com.goby24.goby24.globals.ApiInterface
import com.goby24.goby24.globals.RetrofitInstance
import com.goby24.goby24.globals.SharedPreferenceHelper
import com.goby24.goby24.models.GlobalResponseModel
import com.goby24.goby24.models.SendVerificationEmailModel
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragProfileMain: Fragment() {

    private lateinit var mRootView:View
    private lateinit var mTlHeader:TabLayout
    private lateinit var mSvAboutYou:ScrollView
    private lateinit var mSvAccount:ScrollView
    private var mFlagFromTraveller = true
    private var mFlagFromAboutYou = true

    companion object {
        fun newInstance(): FragProfileMain {
            return FragProfileMain()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(R.layout.frag_profile_main, container, false)
        initView()
        return mRootView;
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden){
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).showOrHideFooter(true)
            }
        }
    }

    private fun initView(){
        mRootView.findViewById<View>(R.id.iv_frag_profile_main_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        mTlHeader = mRootView.findViewById(R.id.tl_frag_profile_main)
        mSvAboutYou = mRootView.findViewById(R.id.sv_frag_profile_main_about_you_root)
        mSvAccount = mRootView.findViewById(R.id.sv_frag_profile_main_account_root)

        mTlHeader.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(mTlHeader.selectedTabPosition){
                    0->{
                        mSvAboutYou.visibility = View.VISIBLE
                        mSvAccount.visibility = View.GONE
                        mFlagFromAboutYou = true
                    }
                    1->{
                        mSvAboutYou.visibility = View.GONE
                        mSvAccount.visibility = View.VISIBLE
                        mFlagFromAboutYou = false
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })

        mRootView.findViewById<TextView>(R.id.tv_frag_profile_main_about_you_traveller).setOnClickListener{
            mRootView.findViewById<TextView>(R.id.tv_frag_profile_main_about_you_traveller).setBackgroundResource(R.drawable.bg_cyan_normal)
            mRootView.findViewById<TextView>(R.id.tv_frag_profile_main_about_you_traveller).setTextColor(resources.getColor(android.R.color.white))
            mRootView.findViewById<TextView>(R.id.tv_frag_profile_main_about_you_driver).setBackgroundResource(R.drawable.rounded_border_cyan_normal)
            mRootView.findViewById<TextView>(R.id.tv_frag_profile_main_about_you_driver).setTextColor(resources.getColor(R.color.txtclr_black))
            mRootView.findViewById<View>(R.id.ll_frag_profile_main_about_you_add_vehicle).isEnabled = false
            mRootView.findViewById<ImageView>(R.id.iv_frag_profile_main_about_you_add_vehicle).setImageResource(R.drawable.ic_add_disabled)
            mRootView.findViewById<TextView>(R.id.tv_frag_profile_main_about_you_add_vehicle).setTextColor(resources.getColor(R.color.txtclr_placeholder_light_gray))
            mRootView.findViewById<ImageView>(R.id.iv_frag_profile_main_about_you_add_vehicle_arrow).alpha = 0.4f
            mFlagFromTraveller = true
        }

        mRootView.findViewById<TextView>(R.id.tv_frag_profile_main_about_you_driver).setOnClickListener{
            mRootView.findViewById<TextView>(R.id.tv_frag_profile_main_about_you_driver).setBackgroundResource(R.drawable.bg_cyan_normal)
            mRootView.findViewById<TextView>(R.id.tv_frag_profile_main_about_you_driver).setTextColor(resources.getColor(android.R.color.white))
            mRootView.findViewById<TextView>(R.id.tv_frag_profile_main_about_you_traveller).setBackgroundResource(R.drawable.rounded_border_cyan_normal)
            mRootView.findViewById<TextView>(R.id.tv_frag_profile_main_about_you_traveller).setTextColor(resources.getColor(R.color.txtclr_black))
            mRootView.findViewById<View>(R.id.ll_frag_profile_main_about_you_add_vehicle).isEnabled = true
            mRootView.findViewById<ImageView>(R.id.iv_frag_profile_main_about_you_add_vehicle).setImageResource(R.drawable.ic_add_selector)
            mRootView.findViewById<TextView>(R.id.tv_frag_profile_main_about_you_add_vehicle).setTextColor(resources.getColor(R.color.txtclr_black))
            mRootView.findViewById<ImageView>(R.id.iv_frag_profile_main_about_you_add_vehicle_arrow).alpha = 1.0f
            mFlagFromTraveller = false
        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_main_avatar_root).setOnClickListener{

        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_main_about_you_add_profile_picture).setOnClickListener{
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).addFrag(FragProfilePictureMain.newInstance(), true, true)
            }
        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_main_about_you_add_mini_bio).setOnClickListener{

        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_main_about_you_edit_travel_preferences).setOnClickListener{
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).addFrag(FragProfileEditTravelPreferences.newInstance(), true, true)
            }
        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_main_about_you_verify_govt_id).setOnClickListener{
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).addFrag(FragVerifyName.newInstance(), true, true)
            }
        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_main_about_you_verify_phone_number).setOnClickListener{
            if (SharedPreferenceHelper.getMobile(requireActivity())!!.isEmpty()) {
                if (requireActivity() is MainActivity) {
                    (requireActivity() as MainActivity).addFrag(
                        FragVerifyPhoneEnterPhone.newInstance(),
                        true,
                        true
                    )
                }
            }else{
                val progressDialog = ProgressDialog(requireActivity())
                progressDialog.show()
                val retIn = RetrofitInstance.getRetrofitInstanceWithAccessTokenAndWithoutBody().create(ApiInterface::class.java)

                retIn.sendVerificationMobile().enqueue(object : Callback<ResponseBody> {
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
                                    val gson = Gson()
                                    val sendOTPMailResponse: GlobalResponseModel = gson.fromJson(
                                        strResponseBody,
                                        GlobalResponseModel::class.java
                                    )
                                    if (sendOTPMailResponse.status && sendOTPMailResponse.result == "OTP sent successfully"){
                                        if (requireActivity() is MainActivity) {
                                            (requireActivity() as MainActivity).addFrag(FragVerifyPhoneOTP.newInstance(), true, true)
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
                            Toast.makeText(requireActivity(), R.string.global_failure, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_main_about_you_verify_email_address).setOnClickListener{
            val strEmail:String = SharedPreferenceHelper.getEmail(requireActivity().applicationContext)!!
            if (strEmail.isEmpty()) {
                if (requireActivity() is MainActivity) {
                    (requireActivity() as MainActivity).addFrag(
                        FragVerifyEmailEnterEmail.newInstance(),
                        true,
                        true
                    )
                }
            }else{
                val data = SendVerificationEmailModel()
                data.email = strEmail
                val progressDialog = ProgressDialog(requireActivity())
                progressDialog.show()
                val retIn = RetrofitInstance.getRetrofitInstanceWithAccessToken().create(ApiInterface::class.java)

                retIn.sendVerificationEmail(data).enqueue(object :
                    Callback<ResponseBody> {
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
                                    val gson = Gson()
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

        mRootView.findViewById<View>(R.id.ll_frag_profile_main_about_you_add_vehicle).setOnClickListener{
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).addFrag(FragAddCarPlateNumber.newInstance(),
                    bAnimation = true,
                    bAdd = true
                )
            }
        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_main_account_ratings_given).setOnClickListener{
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).addFrag(FragRatingsMain.newInstance(), true, true)
            }
        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_main_account_password).setOnClickListener{
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).addFrag(FragCurPwd.newInstance(), true, true)
            }
        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_main_account_available_funds).setOnClickListener{

        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_main_account_bank_details).setOnClickListener{

        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_main_account_add_completed_transactions).setOnClickListener{

        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_main_account_data_protection).setOnClickListener{

        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_main_account_close_account).setOnClickListener{

        }

        mRootView.findViewById<View>(R.id.tv_frag_profile_main_account_logout).setOnClickListener{
            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle(R.string.confirm_logout_title)
            builder.setMessage(R.string.confirm_logout_msg)
            builder.setPositiveButton(R.string.yes) { _, _ ->
                SharedPreferenceHelper.setLoginStatus(requireActivity().applicationContext, false)
            }

            builder.setNegativeButton(R.string.no) { _, _ ->
            }
            builder.show()
        }

        if (mFlagFromAboutYou) {
            mTlHeader.selectTab(mTlHeader.getTabAt(0))
            if (mFlagFromTraveller) {
                mRootView.findViewById<TextView>(R.id.tv_frag_profile_main_about_you_traveller).performClick()
            } else {
                mRootView.findViewById<TextView>(R.id.tv_frag_profile_main_about_you_driver).performClick()
            }
        }else{
            mTlHeader.selectTab(mTlHeader.getTabAt(1))
        }
    }
}