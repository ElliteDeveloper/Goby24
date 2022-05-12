package com.goby24.goby24.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.goby24.goby24.R
import com.goby24.goby24.models.RatingReview

class AdapterReviewListViewItem(context:Context):BaseAdapter() {
    private val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var mContext:Context = context
    private var mArrData:ArrayList<RatingReview> = ArrayList()

    fun setData(context: Context, arrData:ArrayList<RatingReview>) {
        mContext = context
        mArrData = ArrayList()
        if (arrData.size > 0){
            mArrData.addAll(arrData)
        }
    }

    override fun getCount(): Int {
        return mArrData.size
    }

    override fun getItem(p0: Int): Any {
        return mArrData[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, convertView: View?, p2: ViewGroup?): View {
        val view: View
        val vh:ItemHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.slider_ratings_overview, null)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }

        val sliderItem: RatingReview = mArrData[position]
        vh.mIvAvatar.setImageResource(sliderItem.mDrawableID)
        vh.mTvName.text = sliderItem.mStrName
        vh.mTvLevel.text = sliderItem.mStrLevel
        vh.mTvReview.text = sliderItem.mStrReview

        return view
    }

    private class ItemHolder(row: View) {
        var mIvAvatar: ImageView = row.findViewById(R.id.iv_slider_ratings_overview_avatar)
        var mTvName: TextView = row.findViewById(R.id.tv_slider_ratings_overview_name)
        var mTvLevel: TextView = row.findViewById(R.id.tv_slider_ratings_overview_level)
        var mTvReview: TextView = row.findViewById(R.id.tv_slider_ratings_overview_msg)
    }
}