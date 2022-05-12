package com.goby24.goby24.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.goby24.goby24.R
import com.goby24.goby24.models.RatingReview
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderViewAdapterRatingsOverview:
SliderViewAdapter<SliderViewAdapterRatingsOverview.SliderAdapterVH>() {

    var mContext: Context? = null
    lateinit var mArrData:ArrayList<RatingReview>
    fun setData(context: Context) {
        mContext = context
    }

    fun setData(context: Context, arrData:ArrayList<RatingReview>) {
        mContext = context
        mArrData = ArrayList()
        if (arrData != null && arrData.size > 0){
            mArrData.addAll(arrData)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH? {
        val inflate: View =
            LayoutInflater.from(mContext).inflate(R.layout.slider_ratings_overview, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val sliderItem: RatingReview = mArrData[position]
        viewHolder.mIvAvatar.setImageResource(sliderItem.mDrawableID)
        viewHolder.mTvName.setText(sliderItem.mStrName)
        viewHolder.mTvLevel.setText(sliderItem.mStrLevel)
        viewHolder.mTvReview.setText(sliderItem.mStrReview)
        viewHolder.mItemView.tag = sliderItem
    }

    override fun getCount(): Int {
        return mArrData.size
    }

    class SliderAdapterVH(itemView: View) :
        SliderViewAdapter.ViewHolder(itemView) {
        var mItemView: View = itemView
        var mIvAvatar: ImageView = itemView.findViewById(R.id.iv_slider_ratings_overview_avatar)
        var mTvName: TextView = itemView.findViewById(R.id.tv_slider_ratings_overview_name)
        var mTvLevel: TextView = itemView.findViewById(R.id.tv_slider_ratings_overview_level)
        var mTvReview: TextView = itemView.findViewById(R.id.tv_slider_ratings_overview_msg)
    }

}