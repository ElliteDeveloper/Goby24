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
import com.goby24.goby24.models.RideDataForBasicRideAsTravellerModel
import com.idlestar.ratingstar.RatingStarView
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso

class FragRideDetailForBasicAsTraveller: Fragment() {

    private lateinit var mRootView:View
    private lateinit var mIvAvatar:CircularImageView
    private lateinit var mTvName:TextView
    private lateinit var mTvAge:TextView
    private lateinit var mTvAddressStart:TextView
    private lateinit var mTvAddressEnd:TextView
    private lateinit var mRsvRating: RatingStarView
    private lateinit var mTvRatingScore:TextView
    private lateinit var mIvPhoneVerifyStatus: ImageView
    private lateinit var mIvEmailVerifyStatus: ImageView
    private lateinit var mTvAmount:TextView
    private lateinit var mTvDuration:TextView
    private lateinit var mTvDistance:TextView
    private lateinit var mTvDate:TextView
    private lateinit var mTvTime:TextView
    private lateinit var mTvPaymentMethod:TextView
    private lateinit var mTvStatus:TextView
    private lateinit var mTvVehicleInfo:TextView

    companion object {
        lateinit var mData:RideDataForBasicRideAsTravellerModel
        var mFlagIsRider:Boolean = false
        fun newInstance(): FragRideDetailForBasicAsTraveller {
            return FragRideDetailForBasicAsTraveller()
        }

        fun newInstance(rideData:RideDataForBasicRideAsTravellerModel, flagIsRider:Boolean): FragRideDetailForBasicAsTraveller {
            mData = rideData
            mFlagIsRider = flagIsRider
            return FragRideDetailForBasicAsTraveller()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(R.layout.frag_ride_detail_basic_ride_as_traveller, container, false)
        initView()
        return mRootView
    }

    @SuppressLint("SetTextI18n")
    private fun initView(){
        mIvAvatar               = mRootView.findViewById(R.id.iv_frag_ride_detail_avatar)
        mTvName                 = mRootView.findViewById(R.id.tv_frag_ride_detail_name)
        mTvAge                  = mRootView.findViewById(R.id.tv_frag_ride_detail_age)
        mTvAddressStart         = mRootView.findViewById(R.id.tv_frag_ride_detail_start_address)
        mTvAddressEnd           = mRootView.findViewById(R.id.tv_frag_ride_detail_end_address)
        mTvRatingScore          = mRootView.findViewById(R.id.tv_frag_ride_detail_rating)
        mRsvRating              = mRootView.findViewById(R.id.rsv_frag_ride_detail_rating)
        mIvPhoneVerifyStatus    = mRootView.findViewById(R.id.iv_frag_ride_detail_verify_phone_status)
        mIvEmailVerifyStatus    = mRootView.findViewById(R.id.iv_frag_ride_detail_verify_email_status)
        mTvAmount               = mRootView.findViewById(R.id.tv_frag_ride_detail_amount_value)
        mTvDuration             = mRootView.findViewById(R.id.tv_frag_ride_detail_duration_value)
        mTvDistance             = mRootView.findViewById(R.id.tv_frag_ride_detail_distance_value)
        mTvDate                 = mRootView.findViewById(R.id.tv_frag_ride_detail_journey_date_value)
        mTvTime                 = mRootView.findViewById(R.id.tv_frag_ride_detail_start_time_value)
        mTvPaymentMethod        = mRootView.findViewById(R.id.tv_frag_ride_detail_payment_method_value)
        mTvStatus               = mRootView.findViewById(R.id.tv_frag_ride_detail_book_status_value)
        mTvVehicleInfo          = mRootView.findViewById(R.id.tv_frag_ride_detail_vehicle_info_value)

        mRsvRating.isEnabled = false

        if (mFlagIsRider) {
            Picasso.get()
                .load(mData.rider.profile_pic)
                .fit().centerCrop()
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(mIvAvatar)

            val strFN: String = GlobalConstant.globalMyProfile.first_name
            val strLN: String = GlobalConstant.globalMyProfile.last_name

            val strFullName = "$strFN $strLN"
            mTvName.text = strFullName

            mTvAge.text = Utils.getAgeString(requireActivity(), mData.rider.dob)

            try {
                mRsvRating.rating = mData.rider.average_rating_as_rider.toFloat()
                mTvRatingScore.text = mData.rider.average_rating_as_rider
            }catch(e:Exception){
                mRsvRating.rating = 0.0f
                mTvRatingScore.text = "0"
            }

            if (mData.rider.is_mobile_no_verified){
                mIvPhoneVerifyStatus.visibility = View.VISIBLE
            }else{
                mIvPhoneVerifyStatus.visibility = View.INVISIBLE
            }

            if (mData.rider.is_email_verified){
                mIvEmailVerifyStatus.visibility = View.VISIBLE
            }else{
                mIvEmailVerifyStatus.visibility = View.INVISIBLE
            }
        }else{
            Picasso.get()
                .load(mData.rider.profile_pic)
                .fit().centerCrop()
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(mIvAvatar)

            val strFN: String = mData.rider.first_name
            val strLN: String = mData.rider.last_name

            val strFullName = "$strFN $strLN"
            mTvName.text = strFullName

            mTvAge.text = Utils.getAgeString(requireActivity(), mData.rider.dob)

            try {
                mRsvRating.rating = mData.rider.rider_rating.toFloat()
                mTvRatingScore.text = mData.rider.rider_rating
            }catch(e:Exception){
                mRsvRating.rating = 0.0f
                mTvRatingScore.text = "0"
            }

            if (mData.rider.is_mobile_no_verified){
                mIvPhoneVerifyStatus.setImageResource(R.drawable.ic_check_green)
            }else{
                mIvPhoneVerifyStatus.setImageResource(R.drawable.not_verified)
            }

            if (mData.rider.is_email_verified){
                mIvEmailVerifyStatus.setImageResource(R.drawable.ic_check_green)
            }else{
                mIvEmailVerifyStatus.setImageResource(R.drawable.not_verified)
            }
        }

        mTvAmount.text = mData.currency + " " + mData.fare
        mTvDuration.text = mData.journeyTime
        mTvDistance.text = mData.distance
        mTvPaymentMethod.text = mData.payment_method
        mTvStatus.text = mData.book_instantly
        if (mData.rider.brand == "null"){
            mTvVehicleInfo.text = getString(R.string.not_provided)
        }else{
            mTvVehicleInfo.text = mData.rider.brand
        }
        mTvDate.text = mData.date
        mTvTime.text = mData.time
        if (mData.route.size > 0){
            mTvAddressStart.text = mData.route[0]
            mTvAddressEnd.text = mData.route[mData.route.size - 1]
        }else{
            mTvAddressStart.text = ""
            mTvAddressEnd.text = ""
        }

        mRootView.findViewById<View>(R.id.tv_frag_ride_detail_action).setOnClickListener {

        }
        
        mRootView.findViewById<View>(R.id.iv_frag_ride_detail_back).setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}