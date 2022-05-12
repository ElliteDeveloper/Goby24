package com.goby24.goby24.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.goby24.goby24.GlobalConstant
import com.goby24.goby24.LoginActivity
import com.goby24.goby24.MainActivity
import com.goby24.goby24.R
import com.goby24.goby24.globals.SharedPreferenceHelper
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso

class FragDashboard : Fragment() {

    private lateinit var mRootView: View
    private lateinit var mTvName: TextView
    private lateinit var mSbCurrentLevel:SeekBar
    private lateinit var mCivAvatar:CircularImageView

    companion object {
        fun newInstance(): FragDashboard {
            return FragDashboard()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(R.layout.frag_dashboard, container, false)
        initView()
        return mRootView;
    }

    private fun initView() {
        mTvName = mRootView.findViewById(R.id.tv_frag_dashboard_name)
        mSbCurrentLevel = mRootView.findViewById(R.id.sb_frag_dashboard_current_level)
        mCivAvatar = mRootView.findViewById(R.id.iv_frag_dashboard_profile_image)
        Picasso.get()
            .load(GlobalConstant.globalMyProfile.profile_pic)
            .fit().centerCrop()
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo)
            .into(mCivAvatar)

        var strFN: String? = GlobalConstant.globalMyProfile.first_name
        var strLN: String? = GlobalConstant.globalMyProfile.last_name

        var strFullName = "$strFN $strLN"
        mTvName.text = strFullName

//        mSbCurrentLevel.progress = GlobalConstant.globalMyProfile.user_level
        mSbCurrentLevel.progress = 50
        mSbCurrentLevel.isEnabled = false

        mRootView.findViewById<View>(R.id.iv_frag_dashboard_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        mRootView.findViewById<View>(R.id.ll_frag_dashboard_my_rides_root).setOnClickListener {
            if (requireActivity() is MainActivity){
                (requireActivity() as MainActivity).addFrag(
                    FragMyRidesMain.newInstance(),
                    true,
                    true
                )
                (requireActivity() as MainActivity).toggleFooterIcons(4)
            }
        }

        mRootView.findViewById<View>(R.id.ll_frag_dashboard_messages_root).setOnClickListener {

        }

        mRootView.findViewById<View>(R.id.ll_frag_dashboard_rating_root).setOnClickListener {
            if (requireActivity() is MainActivity){
                (requireActivity() as MainActivity).addFrag(
                    FragRatingsMain.newInstance(),
                    true,
                    true
                )
                (requireActivity() as MainActivity).toggleFooterIcons(4)
            }
        }

        mRootView.findViewById<View>(R.id.ll_frag_dashboard_profile_root).setOnClickListener {
            if (requireActivity() is MainActivity){
                (requireActivity() as MainActivity).addFrag(
                    FragProfileMain.newInstance(),
                    true,
                    true
                )
                (requireActivity() as MainActivity).toggleFooterIcons(4)
            }
        }

        mRootView.findViewById<View>(R.id.tv_frag_dashboard_logout).setOnClickListener {
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