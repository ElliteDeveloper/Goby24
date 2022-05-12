package com.goby24.goby24.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.goby24.goby24.MainActivity
import com.goby24.goby24.R
import com.idlestar.ratingstar.RatingStarView
import com.mikhaellopez.circularimageview.CircularImageView

class FragRatingsMain: Fragment() {

    private lateinit var mRootView:View
    private lateinit var mIvAvatar:CircularImageView
    private lateinit var mTvName:TextView
    private lateinit var mTvAge:TextView
    private lateinit var mTvExperienceLevel:TextView
    private lateinit var mTvMessage:TextView

    private lateinit var mRsvRating: RatingStarView
    private lateinit var mTvRatingScore:TextView

    companion object {
        fun newInstance(): FragRatingsMain {
            return FragRatingsMain()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(R.layout.frag_ratings_main, container, false)
        initView()
        return mRootView
    }

    private fun initView(){
        mIvAvatar = mRootView.findViewById(R.id.iv_frag_ratings_main_avatar)
        mTvName = mRootView.findViewById(R.id.tv_frag_ratings_main_name)
        mTvAge = mRootView.findViewById(R.id.tv_frag_ratings_main_age)
        mTvExperienceLevel = mRootView.findViewById(R.id.tv_frag_ratings_main_experience_level)
        mTvMessage = mRootView.findViewById(R.id.tv_frag_ratings_main_msg)
        mTvRatingScore = mRootView.findViewById(R.id.tv_frag_ratings_main_rating)
        mRsvRating = mRootView.findViewById(R.id.rsv_frag_ratings_main_rating)
        mRsvRating.isEnabled = false

        mRootView.findViewById<View>(R.id.iv_frag_ratings_main_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        mRootView.findViewById<View>(R.id.ll_frag_ratings_main_overview).setOnClickListener{
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).addFrag(FragRatingsOverview.newInstance(), true, true)
            }
        }
        mRootView.findViewById<View>(R.id.ll_frag_ratings_main_verify_phone).setOnClickListener{
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).addFrag(FragVerifyPhoneEnterPhone.newInstance(), true, true)
            }
        }

        mRootView.findViewById<View>(R.id.ll_frag_ratings_main_verify_email).setOnClickListener{
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).addFrag(FragVerifyEmailEnterEmail.newInstance(), true, true)
            }
        }
    }
}