package com.goby24.goby24.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goby24.goby24.R

class FragUploadPassport: Fragment() {

    private lateinit var mRootView:View

    companion object {
        fun newInstance(): FragUploadPassport {
            return FragUploadPassport()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(R.layout.frag_upload_passport, container, false)
        initView()
        return mRootView
    }

    private fun initView(){
        mRootView.findViewById<View>(R.id.iv_frag_upload_passport_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        mRootView.findViewById<View>(R.id.tv_frag_upload_passport_continue).setOnClickListener {
        }
    }
}