package com.goby24.goby24.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goby24.goby24.LoginActivity
import com.goby24.goby24.MainActivity
import com.goby24.goby24.R

class FragSigninFirst: Fragment() {

    private lateinit var mRootView:View

    companion object {
        fun newInstance(): FragSigninFirst {
            return FragSigninFirst()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.frag_signin_first, container, false)
        initView()
        return mRootView;
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden){
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).showOrHideFooter(false)
            }
        }
    }

    private fun initView(){
        mRootView.findViewById<View>(R.id.tv_frag_signin_first_continue).setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}