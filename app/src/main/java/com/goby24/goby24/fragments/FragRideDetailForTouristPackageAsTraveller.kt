package com.goby24.goby24.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.goby24.goby24.GlobalConstant
import com.goby24.goby24.R
import com.goby24.goby24.globals.Utils
import com.goby24.goby24.models.RideDataForRideRequestAsTravellerModel
import com.goby24.goby24.models.RideDataForTouristPackageAsTravellerModel
import com.idlestar.ratingstar.RatingStarView
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso

class FragRideDetailForTouristPackageAsTraveller : Fragment() {

    private lateinit var mRootView: View
    private lateinit var mIvRiderAvatar: CircularImageView
    private lateinit var mTvRiderName: TextView
    private lateinit var mTvRiderAge: TextView
    private lateinit var mIvTravellerAvatar: CircularImageView
    private lateinit var mTvTravellerName: TextView
    private lateinit var mTvAddressStart: TextView
    private lateinit var mTvAddressEnd: TextView
    private lateinit var mRsvRating: RatingStarView
    private lateinit var mTvRatingScore: TextView
    private lateinit var mIvPhoneVerifyStatus: ImageView
    private lateinit var mIvEmailVerifyStatus: ImageView
    private lateinit var mRsvRatingTravller: RatingStarView
    private lateinit var mTvRatingScoreTravller: TextView
    private lateinit var mIvPhoneVerifyStatusTravller: ImageView
    private lateinit var mIvEmailVerifyStatusTravller: ImageView
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
        lateinit var mData: RideDataForTouristPackageAsTravellerModel
        var mFlagIsRider: Boolean = false
        fun newInstance(): FragRideDetailForTouristPackageAsTraveller {
            return FragRideDetailForTouristPackageAsTraveller()
        }

        fun newInstance(
            rideData: RideDataForTouristPackageAsTravellerModel
        ): FragRideDetailForTouristPackageAsTraveller {
            mData = rideData
            return FragRideDetailForTouristPackageAsTraveller()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mRootView =
            inflater.inflate(R.layout.frag_ride_detail_tourist_package_as_traveller, container, false)
        initView()
        return mRootView
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        mIvRiderAvatar = mRootView.findViewById(R.id.iv_frag_ride_detail_avatar)
        mTvRiderName = mRootView.findViewById(R.id.tv_frag_ride_detail_name)
        mTvRiderAge = mRootView.findViewById(R.id.tv_frag_ride_detail_age)
        mIvTravellerAvatar = mRootView.findViewById(R.id.iv_frag_ride_detail_traveller_info_avatar)
        mTvTravellerName = mRootView.findViewById(R.id.tv_frag_ride_detail_traveller_info_name)
        mTvAddressStart = mRootView.findViewById(R.id.tv_frag_ride_detail_start_address)
        mTvAddressEnd = mRootView.findViewById(R.id.tv_frag_ride_detail_end_address)
        mTvRatingScore = mRootView.findViewById(R.id.tv_frag_ride_detail_rating)
        mRsvRating = mRootView.findViewById(R.id.rsv_frag_ride_detail_rating)
        mIvPhoneVerifyStatus = mRootView.findViewById(R.id.iv_frag_ride_detail_verify_phone_status)
        mIvEmailVerifyStatus = mRootView.findViewById(R.id.iv_frag_ride_detail_verify_email_status)
        mTvRatingScoreTravller =
            mRootView.findViewById(R.id.tv_frag_ride_detail_traveller_info_rating)
        mRsvRatingTravller = mRootView.findViewById(R.id.rsv_frag_ride_detail_traveller_info_rating)
        mIvPhoneVerifyStatusTravller =
            mRootView.findViewById(R.id.iv_frag_ride_detail_traveller_info_verify_phone_status)
        mIvEmailVerifyStatusTravller =
            mRootView.findViewById(R.id.iv_frag_ride_detail_traveller_info_verify_email_status)
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
        mRsvRatingTravller.isEnabled = false

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
            mRsvRating.rating = mData.provider.average_rating_as_rider.toFloat()
            mTvRatingScore.text = mData.provider.average_rating_as_rider
        } catch (e: Exception) {
            mRsvRating.rating = 0.0f
            mTvRatingScore.text = "0"
        }

        if (mData.provider.is_mobile_no_verified) {
            mIvPhoneVerifyStatus.setImageResource(R.drawable.ic_check_green)
        } else {
            mIvPhoneVerifyStatus.setImageResource(R.drawable.not_verified)
        }

        if (mData.provider.is_email_verified) {
            mIvEmailVerifyStatus.setImageResource(R.drawable.ic_check_green)
        } else {
            mIvEmailVerifyStatus.setImageResource(R.drawable.not_verified)
        }

        Picasso.get()
            .load(mData.user.profile_pic)
            .fit().centerCrop()
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo)
            .into(mIvTravellerAvatar)

        mTvTravellerName.text = mData.user.first_name + " " + mData.user.last_name

        try {
            mRsvRatingTravller.rating = mData.user.average_rating_as_traveller.toFloat()
            mTvRatingScoreTravller.text = mData.user.average_rating_as_traveller
        } catch (e: Exception) {
            mRsvRatingTravller.rating = 0.0f
            mTvRatingScoreTravller.text = "0"
        }

        if (mData.user.is_mobile_no_verified) {
            mIvPhoneVerifyStatusTravller.setImageResource(R.drawable.ic_check_green)
        } else {
            mIvPhoneVerifyStatusTravller.setImageResource(R.drawable.not_verified)
        }

        if (mData.user.is_email_verified) {
            mIvEmailVerifyStatusTravller.setImageResource(R.drawable.ic_check_green)
        } else {
            mIvEmailVerifyStatusTravller.setImageResource(R.drawable.not_verified)
        }


        mTvAmount.text = mData.currency + " " + mData.base_fare
        mTvCountry.text = mData.tourist_package.country
        mTvDays.text = mData.tourist_package.no_of_days.toString()
        mTvTourists.text = mData.tourist_package.no_of_tourist.toString()
        mTvPaymentMethod.text = ""
        mTvStatus.text = mData.status
        if (mData.provider.brand == "null" || mData.provider.brand == "") {
            mTvVehicleInfo.text = getString(R.string.not_provided)
        } else {
            mTvVehicleInfo.text = mData.provider.brand
        }
        mTvDate.text = mData.arrival_date
        mTvTime.text = mData.tourist_package.time_from + " - " + mData.tourist_package.time_to

        try {
            mTvAddressStart.text = mData.tourist_package.package_name
        } catch (e: Exception) {
        }
        try {
            mTvAddressEnd.text = mData.tourist_package.spot.spot_name
        } catch (e: Exception) {
        }

        mRootView.findViewById<View>(R.id.iv_frag_ride_detail_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        mRootView.findViewById<View>(R.id.tv_frag_ride_detail_tourist_package_as_traveller_contact_rider).setOnClickListener {

        }
    }
}