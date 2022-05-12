package com.goby24.goby24.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.goby24.goby24.MainActivity
import com.goby24.goby24.R
import com.goby24.goby24.globals.SharedPreferenceHelper

class FragProfileStarter: Fragment() {

    private lateinit var mRootView:View

    companion object {
        fun newInstance(): FragProfileStarter {
            return FragProfileStarter()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(R.layout.frag_profile_starter, container, false)
        initView()
        return mRootView
    }

    private fun initView(){
        mRootView.findViewById<View>(R.id.iv_frag_profile_starter_close).setOnClickListener {
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).addFrag(FragHome.newInstance(),
                    bAnimation = true,
                    bAdd = false
                )
                (requireActivity() as MainActivity).toggleFooterIcons(0)
            }
        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_starter_dashboard).setOnClickListener {
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).addFrag(FragDashboard.newInstance(), true,
                    bAdd = true
                )
            }
        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_starter_profile).setOnClickListener {
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).addFrag(FragProfileMain.newInstance(),
                    bAnimation = true,
                    bAdd = true
                )
            }
        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_starter_inbox).setOnClickListener {

        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_starter_my_rides).setOnClickListener {
            if (requireActivity() is MainActivity){
                (requireActivity() as MainActivity).addFrag(
                    FragMyRidesMain.newInstance(),
                    bAnimation = true,
                    bAdd = true
                )
                (requireActivity() as MainActivity).toggleFooterIcons(4)
            }
        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_starter_payments_refunds).setOnClickListener {

        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_starter_change_language).setOnClickListener {

        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_starter_privacy_policy).setOnClickListener {

        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_starter_tac).setOnClickListener {

        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_starter_contact_support).setOnClickListener {

        }

        mRootView.findViewById<View>(R.id.ll_frag_profile_starter_logout).setOnClickListener {
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
    }
}