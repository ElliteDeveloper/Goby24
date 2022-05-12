package com.goby24.goby24.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.goby24.goby24.R
import com.goby24.goby24.models.RatingReview
import com.idlestar.ratingstar.RatingStarView
import com.mikhaellopez.circularimageview.CircularImageView

class FragRatingDetail: Fragment() {

    private lateinit var mRootView:View
    private lateinit var mIvAvatar:CircularImageView
    private lateinit var mTvName:TextView
    private lateinit var mTvAge:TextView
    private lateinit var mRsvBookride: RatingStarView
    private lateinit var mRsvProfessionalism: RatingStarView
    private lateinit var mRsvDriving: RatingStarView
    private lateinit var mRsvComfort: RatingStarView
    private lateinit var mRsvCleanliness: RatingStarView
    private lateinit var mRsvMusic: RatingStarView
    private lateinit var mRsvCarSmell: RatingStarView
    private lateinit var mRsvConversation: RatingStarView
    private lateinit var mRsvPickup: RatingStarView
    private lateinit var mEdRemark:EditText

    companion object {
        private lateinit var mData:RatingReview
        fun newInstance(data:RatingReview): FragRatingDetail {
            mData = data
            return FragRatingDetail()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(R.layout.frag_ratings_detail, container, false)
        initView()
        return mRootView
    }

    private fun initView(){
        mIvAvatar               = mRootView.findViewById(R.id.iv_frag_rating_detail_avatar)
        mTvName                 = mRootView.findViewById(R.id.tv_frag_rating_detail_name)
        mTvAge                  = mRootView.findViewById(R.id.tv_frag_rating_detail_age)
        mRsvBookride            = mRootView.findViewById(R.id.rsv_frag_rating_detail_bookride)
        mRsvProfessionalism     = mRootView.findViewById(R.id.rsv_frag_rating_detail_professionalism)
        mRsvDriving             = mRootView.findViewById(R.id.rsv_frag_rating_detail_driving)
        mRsvComfort             = mRootView.findViewById(R.id.rsv_frag_rating_detail_comfort)
        mRsvCleanliness         = mRootView.findViewById(R.id.rsv_frag_rating_detail_cleanliness)
        mRsvMusic               = mRootView.findViewById(R.id.rsv_frag_rating_detail_music)
        mRsvCarSmell            = mRootView.findViewById(R.id.rsv_frag_rating_detail_car_smell)
        mRsvConversation        = mRootView.findViewById(R.id.rsv_frag_rating_detail_conversation)
        mRsvPickup              = mRootView.findViewById(R.id.rsv_frag_rating_detail_pickup)
        mEdRemark               = mRootView.findViewById(R.id.ed_frag_rating_detail_remark)

        if (mData != null) {
            mRsvBookride.rating = mData.m_fBookride
            mRsvProfessionalism.rating = mData.m_fProfessionalism
            mRsvDriving.rating = mData.m_fDriving
            mRsvComfort.rating = mData.m_fComfort
            mRsvCleanliness.rating = mData.m_fCleanliness
            mRsvMusic.rating = mData.m_fMusic
            mRsvCarSmell.rating = mData.m_fCarSmell
            mRsvConversation.rating = mData.m_fConversation
            mRsvPickup.rating = mData.m_fPickup
            mEdRemark.setText(mData.mStrReview)
        }

        mRootView.findViewById<View>(R.id.iv_frag_rating_detail_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        mRootView.findViewById<View>(R.id.tv_frag_rating_detail_submit).setOnClickListener {
            mData.m_fBookride = mRsvBookride.rating
            mData.m_fProfessionalism = mRsvProfessionalism.rating
            mData.m_fDriving = mRsvDriving.rating
            mData.m_fComfort = mRsvComfort.rating
            mData.m_fCleanliness = mRsvCleanliness.rating
            mData.m_fMusic = mRsvMusic.rating
            mData.m_fCarSmell = mRsvCarSmell.rating
            mData.m_fConversation = mRsvConversation.rating
            mData.m_fPickup = mRsvPickup.rating
            mData.mStrReview = mEdRemark.text.toString()
            requireActivity().onBackPressed()
        }
    }
}