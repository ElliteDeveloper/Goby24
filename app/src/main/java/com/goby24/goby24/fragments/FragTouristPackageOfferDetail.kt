package com.goby24.goby24.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.goby24.goby24.R
import com.goby24.goby24.models.TouristPackageOfferModel
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso

class FragTouristPackageOfferDetail: Fragment() {

    private lateinit var mRootView:View
    private lateinit var mTvTitle:TextView
    private lateinit var mIvBanner:RoundedImageView
    private lateinit var mTvDetail:TextView
    private lateinit var mTvDates:TextView
    private lateinit var mTvPrice:TextView
    private lateinit var mTvTourists:TextView
    private lateinit var mTvTime:TextView

    companion object {
        lateinit var mData: TouristPackageOfferModel
        fun newInstance(): FragTouristPackageOfferDetail {
            return FragTouristPackageOfferDetail()
        }

        fun newInstance(rideData: TouristPackageOfferModel): FragTouristPackageOfferDetail {
            mData = rideData
            return FragTouristPackageOfferDetail()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(R.layout.frag_tourist_package_offers_detail, container, false)
        initView()
        return mRootView
    }

    @SuppressLint("SetTextI18n")
    private fun initView(){

        mIvBanner = mRootView.findViewById(R.id.riv_frag_tourist_package_offers_detail)
        mTvTitle = mRootView.findViewById(R.id.tv_frag_tourist_package_offers_detail_title)
        mTvDetail = mRootView.findViewById(R.id.tv_frag_tourist_package_offers_detail)
        mTvDates = mRootView.findViewById(R.id.tv_frag_tourist_package_offers_detail_days)
        mTvTourists = mRootView.findViewById(R.id.tv_frag_tourist_package_offers_detail_tourists)
        mTvPrice = mRootView.findViewById(R.id.tv_frag_tourist_package_offers_detail_price)
        mTvTime = mRootView.findViewById(R.id.tv_frag_tourist_package_offers_detail_time)

        mTvTitle.text = mData.package_name
        mTvDetail.text = mData.package_brief
        mTvDates.text = String.format(getString(R.string.days_format_string), mData.no_of_days)
        mTvDates.text = String.format(getString(R.string.no_tourists_format_string), mData.no_of_tourists)
        mTvPrice.text = mData.package_price + mData.currency
        mTvTime.text = mData.time_from + "-" + mData.time_to

        Picasso.get()
            .load(mData.package_banner)
            .fit().centerCrop()
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo)
            .into(mIvBanner)
        
        mRootView.findViewById<View>(R.id.iv_frag_tourist_package_offers_detail_back).setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}