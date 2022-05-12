package com.goby24.goby24.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goby24.goby24.MainActivity
import com.goby24.goby24.R

class FragProfilePictureMain: Fragment() {

    private lateinit var mRootView:View

    companion object {
        fun newInstance(): FragProfilePictureMain {
            return FragProfilePictureMain()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.frag_profile_picture_main, container, false)
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
        mRootView.findViewById<View>(R.id.iv_frag_profile_picture_main_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        mRootView.findViewById<View>(R.id.tv_frag_profile_picture_main_take_photo).setOnClickListener {
            requireActivity().onBackPressed()
        }

        mRootView.findViewById<View>(R.id.tv_frag_profile_picture_main_choose_picture).setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}