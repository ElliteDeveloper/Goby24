package com.goby24.goby24.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.goby24.goby24.R

class SpinnerAdapterPlateNumber(val context: Context) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val mCtx:Context = context
    private val mArrData:ArrayList<PhonePrefixDataModel> = ArrayList()

    private val mArrFlagDrawableIds = intArrayOf(
        R.drawable.flag_swit,
        R.drawable.flag_fran,
        R.drawable.flag_ital,
        R.drawable.flag_indi
    )

    private val mArrCountryNameIds = intArrayOf(
        R.string.plate_number_swit,
        R.string.plate_number_fran,
        R.string.plate_number_ital,
        R.string.plate_number_indi
    )

    init{
        for (i in mArrFlagDrawableIds.indices){
            mArrData.add(PhonePrefixDataModel(mCtx.resources.getString(mArrCountryNameIds[i]), mArrFlagDrawableIds[i]))
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

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.spitem_mobile_phone_prefix, null)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.mTvPrefix.text = mArrData[position].mStrPrefix
        vh.mIvFlag.setImageResource(mArrData[position].mFlagDrawableID)

        return view
    }

    private class PhonePrefixDataModel(strPrefix:String, flagID:Int){
        val mStrPrefix:String = strPrefix
        val mFlagDrawableID:Int = flagID
    }

    private class ItemHolder(row: View?) {
        val mTvPrefix: TextView = row?.findViewById(R.id.tv_spitem_mobile_phone_prefix_country) as TextView
        val mIvFlag: ImageView = row?.findViewById(R.id.iv_spitem_mobile_phone_prefix_flag) as ImageView
    }
}