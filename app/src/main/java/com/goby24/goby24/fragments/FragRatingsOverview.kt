package com.goby24.goby24.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.goby24.goby24.MainActivity
import com.goby24.goby24.R
import com.goby24.goby24.models.RatingReview
import com.goby24.goby24.ui.SliderViewAdapterRatingsOverview
import com.idlestar.ratingstar.RatingStarView
import com.mikhaellopez.circularimageview.CircularImageView
import com.smarteist.autoimageslider.SliderView

class FragRatingsOverview: Fragment() {

    private lateinit var mRootView:View
    private lateinit var mTvRatingScore:TextView
    private lateinit var mRsvRating: RatingStarView
    private lateinit var mTvTotalRatings:TextView

    private lateinit var mSbStar5Percent:SeekBar
    private lateinit var mTvStar5Percent:TextView
    private lateinit var mSbStar4Percent:SeekBar
    private lateinit var mTvStar4Percent:TextView
    private lateinit var mSbStar3Percent:SeekBar
    private lateinit var mTvStar3Percent:TextView
    private lateinit var mSbStar2Percent:SeekBar
    private lateinit var mTvStar2Percent:TextView
    private lateinit var mSbStar1Percent:SeekBar
    private lateinit var mTvStar1Percent:TextView

    private lateinit var mSliderReviews: SliderView
    private lateinit var mAdapterSlider:SliderViewAdapterRatingsOverview
    private lateinit var mArrData:ArrayList<RatingReview>

    companion object {
        fun newInstance(): FragRatingsOverview {
            return FragRatingsOverview()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(R.layout.frag_ratings_overview, container, false)
        initData()
        initView()
        return mRootView
    }

    private fun initData(){
        mArrData = ArrayList()
        for (i in 0 until 5){
            val item = RatingReview("Lorem Ipsum", "Excellent", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempou")
            mArrData.add(item)
        }
    }

    private fun initView(){
        mTvRatingScore = mRootView.findViewById(R.id.tv_frag_ratings_overview_rating)
        mRsvRating = mRootView.findViewById(R.id.rsv_frag_ratings_overview_rating)
        mTvTotalRatings = mRootView.findViewById(R.id.tv_frag_ratings_overview_total)
        mSbStar5Percent = mRootView.findViewById(R.id.sb_frag_ratings_overview_star5_percent)
        mTvStar5Percent = mRootView.findViewById(R.id.tv_frag_ratings_overview_star5_percent)
        mSbStar4Percent = mRootView.findViewById(R.id.sb_frag_ratings_overview_star4_percent)
        mTvStar4Percent = mRootView.findViewById(R.id.tv_frag_ratings_overview_star4_percent)
        mSbStar3Percent = mRootView.findViewById(R.id.sb_frag_ratings_overview_star3_percent)
        mTvStar3Percent = mRootView.findViewById(R.id.tv_frag_ratings_overview_star3_percent)
        mSbStar2Percent = mRootView.findViewById(R.id.sb_frag_ratings_overview_star2_percent)
        mTvStar2Percent = mRootView.findViewById(R.id.tv_frag_ratings_overview_star2_percent)
        mSbStar1Percent = mRootView.findViewById(R.id.sb_frag_ratings_overview_star1_percent)
        mTvStar1Percent = mRootView.findViewById(R.id.tv_frag_ratings_overview_star1_percent)

        mSliderReviews = mRootView.findViewById(R.id.slider_frag_ratings_overview)
        mAdapterSlider = SliderViewAdapterRatingsOverview()
        mAdapterSlider.setData(requireActivity(), mArrData)
        mSliderReviews.setSliderAdapter(mAdapterSlider)
        mSliderReviews.startAutoCycle()


        mRootView.findViewById<View>(R.id.iv_frag_ratings_overview_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        mRootView.findViewById<View>(R.id.tv_frag_ratings_overview_reviews_show_details).setOnClickListener{
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).addFrag(FragRatingsListView.newInstance(), true, true)
            }
        }
    }
}