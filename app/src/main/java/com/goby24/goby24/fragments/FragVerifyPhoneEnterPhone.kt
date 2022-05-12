package com.goby24.goby24.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.goby24.goby24.MainActivity
import com.goby24.goby24.R
import com.rilixtech.widget.countrycodepicker.CountryCodePicker

class FragVerifyPhoneEnterPhone: Fragment() {

    private lateinit var mRootView:View
    private lateinit var mEdPhone:EditText
    private lateinit var mCcpPhonePrefix: CountryCodePicker

    companion object {
        fun newInstance(): FragVerifyPhoneEnterPhone {
            return FragVerifyPhoneEnterPhone()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(R.layout.frag_verify_phone_enter_phone, container, false)
        initView()
        return mRootView
    }

    private fun initView(){
        mEdPhone = mRootView.findViewById(R.id.ed_frag_verify_phone_enter_phone)
        mCcpPhonePrefix = mRootView.findViewById(R.id.ccp_frag_verify_phone_enter_phone)
        mCcpPhonePrefix.registerPhoneNumberTextView(mEdPhone)

        mRootView.findViewById<View>(R.id.iv_frag_verify_phone_enter_phone_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        mRootView.findViewById<View>(R.id.tv_frag_verify_phone_enter_phone_next).setOnClickListener {
            val strMobile = mCcpPhonePrefix.selectedCountryCodeWithPlus + mEdPhone.text.toString()
            if (strMobile.isEmpty()){
                Toast.makeText(requireActivity(), R.string.enter_phone, Toast.LENGTH_LONG).show()
                mEdPhone.requestFocus()
                return@setOnClickListener
            }
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).addFrag(FragVerifyPhoneOTP.newInstance(strMobile), true, true)
            }
        }
    }
}