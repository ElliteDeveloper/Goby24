package com.goby24.goby24.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.goby24.goby24.MainActivity
import com.goby24.goby24.R
import com.goby24.goby24.fragments.FragHome
import com.smarteist.autoimageslider.SliderViewAdapter
import java.util.ArrayList

class SliderViewAdapterHomepage:
SliderViewAdapter<SliderViewAdapterHomepage.SliderAdapterVH>() {

    var mContext: Context? = null
    private var mSliderItems = intArrayOf(
        R.drawable.logo1,
        R.drawable.logo2
    )

    fun setData(context: Context) {
        mContext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH? {
        val inflate: View =
            LayoutInflater.from(mContext).inflate(R.layout.slider_home, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val sliderItem: Int = mSliderItems[position]
        viewHolder.imageViewBackground.setImageResource(sliderItem)
        viewHolder.mItemView.tag = sliderItem
    }

    override fun getCount(): Int {
        return mSliderItems.size
    }

    class SliderAdapterVH(itemView: View) :
        SliderViewAdapter.ViewHolder(itemView) {
        lateinit var mItemView: View
        var imageViewBackground: ImageView

        init {
            imageViewBackground = itemView.findViewById(R.id.iv_slider_home)
            mItemView = itemView
        }
    }

}