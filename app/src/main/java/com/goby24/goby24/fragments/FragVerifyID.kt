package com.goby24.goby24.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goby24.goby24.MainActivity
import com.goby24.goby24.R

class FragVerifyID: Fragment() {

    private lateinit var mRootView:View

    companion object {
        fun newInstance(): FragVerifyID {
            return FragVerifyID()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(R.layout.frag_verify_id, container, false)
        initView()
        return mRootView
    }

    private fun initView(){
        mRootView.findViewById<View>(R.id.iv_frag_verify_id_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        mRootView.findViewById<View>(R.id.tv_frag_verify_id_continue).setOnClickListener {
        }

        mRootView.findViewById<View>(R.id.ll_frag_verify_id_passport_root).setOnClickListener {
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).addFrag(FragUploadPassport.newInstance(), true, true)
            }
        }

        mRootView.findViewById<View>(R.id.ll_frag_verify_id_aadhaar_root).setOnClickListener {
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).addFrag(FragUploadPhotoCard.newInstance(), true, true)
            }
        }

    }
}