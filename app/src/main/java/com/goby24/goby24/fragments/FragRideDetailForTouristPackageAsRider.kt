package com.goby24.goby24.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.goby24.goby24.GlobalConstant
import com.goby24.goby24.R
import com.goby24.goby24.globals.Utils
import com.goby24.goby24.models.RideDataForRideRequestAsTravellerModel
import com.goby24.goby24.models.RideDataForTouristPackageAsRiderModel
import com.goby24.goby24.models.RideDataForTouristPackageAsTravellerModel
import com.idlestar.ratingstar.RatingStarView
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso

class FragRideDetailForTouristPackageAsRider : Fragment() {

    private lateinit var mRootView: View
    private lateinit var mIvRiderAvatar: CircularImageView
    private lateinit var mTvRiderName: TextView
    private lateinit var mTvRiderAge: TextView
    private lateinit var mTvAddressStart: TextView
    private lateinit var mTvAddressEnd: TextView
    private lateinit var mRsvRating: RatingStarView
    private lateinit var mTvRatingScore: TextView
    private lateinit var mIvPhoneVerifyStatus: ImageView
    private lateinit var mIvEmailVerifyStatus: ImageView
    private lateinit var mTvAmount: TextView
    private lateinit var mTvCountry: TextView
    private lateinit var mTvDays: TextView
    private lateinit var mTvTourists: TextView
    private lateinit var mTvDate: TextView
    private lateinit var mTvTime: TextView
    private lateinit var mTvPaymentMethod: TextView
    private lateinit var mTvStatus: TextView
    private lateinit var mTvVehicleInfo: TextView

    companion object {
        lateinit var mData: RideDataForTouristPackageAsRiderModel
        fun newInstance(): FragRideDetailForTouristPackageAsRider {
            return FragRideDetailForTouristPackageAsRider()
        }

        fun newInstance(
            rideData: RideDataForTouristPackageAsRiderModel
        ): FragRideDetailForTouristPackageAsRider {
            mData = rideData
            return FragRideDetailForTouristPackageAsRider()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mRootView =
            inflater.inflate(R.layout.frag_ride_detail_tourist_package_as_rider, container, false)
        initView()
        return mRootView
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    private fun initView() {
        mIvRiderAvatar = mRootView.findViewById(R.id.iv_frag_ride_detail_avatar)
        mTvRiderName = mRootView.findViewById(R.id.tv_frag_ride_detail_name)
        mTvRiderAge = mRootView.findViewById(R.id.tv_frag_ride_detail_age)
        mTvAddressStart = mRootView.findViewById(R.id.tv_frag_ride_detail_start_address)
        mTvAddressEnd = mRootView.findViewById(R.id.tv_frag_ride_detail_end_address)
        mTvRatingScore = mRootView.findViewById(R.id.tv_frag_ride_detail_rating)
        mRsvRating = mRootView.findViewById(R.id.rsv_frag_ride_detail_rating)
        mIvPhoneVerifyStatus = mRootView.findViewById(R.id.iv_frag_ride_detail_verify_phone_status)
        mIvEmailVerifyStatus = mRootView.findViewById(R.id.iv_frag_ride_detail_verify_email_status)
        mTvAmount = mRootView.findViewById(R.id.tv_frag_ride_detail_amount_value)
        mTvPaymentMethod = mRootView.findViewById(R.id.tv_frag_ride_detail_payment_method_value)
        mTvCountry = mRootView.findViewById(R.id.tv_frag_ride_detail_country_value)
        mTvDays = mRootView.findViewById(R.id.tv_frag_ride_detail_days_value)
        mTvTourists = mRootView.findViewById(R.id.tv_frag_ride_detail_tourists_value)
        mTvDate = mRootView.findViewById(R.id.tv_frag_ride_detail_journey_date_value)
        mTvTime = mRootView.findViewById(R.id.tv_frag_ride_detail_start_time_value)
        mTvStatus = mRootView.findViewById(R.id.tv_frag_ride_detail_status)
        mTvVehicleInfo = mRootView.findViewById(R.id.tv_frag_ride_detail_vehicle_info_value)

        mRsvRating.isEnabled = false

        Picasso.get()
            .load(mData.provider.profile_pic)
            .fit().centerCrop()
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo)
            .into(mIvRiderAvatar)

        val strFN: String = mData.provider.first_name
        val strLN: String = mData.provider.last_name

        val strFullName = "$strFN $strLN"
        mTvRiderName.text = strFullName

        mTvRiderAge.text = Utils.getAgeString(requireActivity(), mData.provider.dob)

        try {
            mRsvRating.rating = mData.provider.rider_rating.toFloat()
            mTvRatingScore.text = mData.provider.rider_rating
        } catch (e: Exception) {
            mRsvRating.rating = 0.0f
            mTvRatingScore.text = "0"
        }

        if (mData.provider.is_mobile_no_verified) {
            mIvPhoneVerifyStatus.visibility = View.VISIBLE
        } else {
            mIvPhoneVerifyStatus.visibility = View.INVISIBLE
        }

        if (mData.provider.is_email_verified) {
            mIvEmailVerifyStatus.visibility = View.VISIBLE
        } else {
            mIvEmailVerifyStatus.visibility = View.INVISIBLE
        }

        if (mData.travellers != null){
            var inflater = LayoutInflater.from(requireActivity())
            var llRoot = mRootView.findViewById<LinearLayout>(R.id.ll_frag_ride_detail_tourist_detail_root)
            for (i in 0 until mData.travellers.size){
                var v:View = inflater.inflate(R.layout.svitem_ride_detail_tourist_package_tourist, null)
                var lp:LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                v.layoutParams = lp
                Picasso.get()
                    .load(mData.travellers[i].profile_pic)
                    .fit().centerCrop()
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.logo)
                    .into(v.findViewById<ImageView>(R.id.iv_svitem_ride_detail_tourist_package_tourist_avatar))
                v.findViewById<TextView>(R.id.tv_svitem_ride_detail_tourist_package_tourist_name).text = mData.travellers[i].first_name + " " + mData.travellers.get(i).last_name
                v.findViewById<TextView>(R.id.tv_svitem_ride_detail_tourist_package_tourist_rating).text = mData.travellers[i].average_rating_as_traveller
                v.findViewById<View>(R.id.tv_svitem_ride_detail_tourist_package_tourist_contact_tourist).setOnClickListener {

                }
                llRoot.addView(v)
            }
        }

        mTvAmount.text = mData.currency + " " + mData.package_price
        mTvCountry.text = mData.country
        mTvDays.text = mData.no_of_days.toString()
        mTvTourists.text = mData.no_of_tourist.toString()
        mTvPaymentMethod.text = ""
        mTvStatus.text = mData.package_status
        if (mData.vehicle_info.brand == "null" || mData.vehicle_info.brand == "") {
            mTvVehicleInfo.text = getString(R.string.not_provided)
        } else {
            mTvVehicleInfo.text = mData.vehicle_info.brand
        }
        mTvDate.text = mData.date_from + " " + mData.date_to
        mTvTime.text = mData.time_from + " - " + mData.time_to

        try {
            mTvAddressStart.text = mData.package_name
        } catch (e: Exception) {
        }
        try {
            mTvAddressEnd.text = mData.spot.spot_name
        } catch (e: Exception) {
        }

        mRootView.findViewById<View>(R.id.iv_frag_ride_detail_back).setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}