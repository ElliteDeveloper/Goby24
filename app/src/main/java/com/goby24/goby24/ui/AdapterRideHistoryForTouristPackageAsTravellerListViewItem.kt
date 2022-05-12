package com.goby24.goby24.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.goby24.goby24.R
import com.goby24.goby24.models.RideDataForTouristPackageAsTravellerModel

class AdapterRideHistoryForTouristPackageAsTravellerListViewItem(context:Context):BaseAdapter() {
    private val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var mContext:Context = context
    private var mArrData:ArrayList<RideDataForTouristPackageAsTravellerModel> = ArrayList()

    fun setData(context: Context, arrData:ArrayList<RideDataForTouristPackageAsTravellerModel>) {
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

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, p2: ViewGroup?): View {
        val view: View
        val vh:ItemHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.rvitem_rides_tourist_package_as_traveller, null)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }

        val item: RideDataForTouristPackageAsTravellerModel = mArrData[position]
        vh.mTvDateTime.text = item.arrival_date
        vh.mTvStatus.text = item.status
        if (item.tourist_package.package_name != "null"){
            vh.mTvAddressStart.text = item.tourist_package.package_name
        }else{
            vh.mTvAddressStart.text = ""
        }
        vh.mTvDuration.text = item.tourist_package.time_from + " - " + item.tourist_package.time_to
        vh.mTvCost.text = item.currency + " " + item.price
        if (item.status.contains("completed", true)){
            vh.mTvAction.visibility = View.VISIBLE
            vh.mTvAction.text = mContext.getString(R.string.review)
        }else{
            vh.mTvAction.visibility = View.GONE
        }
        vh.mTvAction.setOnClickListener{

        }
        return view
    }

    private class ItemHolder(row: View) {
        var mTvDateTime: TextView = row.findViewById(R.id.tv_rvitem_myrides_datetime)
        var mTvStatus: TextView = row.findViewById(R.id.tv_rvitem_myrides_status)
        var mTvAddressStart: TextView = row.findViewById(R.id.tv_rvitem_myrides_address_start)
        var mTvDuration: TextView = row.findViewById(R.id.tv_rvitem_myrides_duration)
        var mTvCost: TextView = row.findViewById(R.id.tv_rvitem_myrides_cost)
        var mTvAction: TextView = row.findViewById(R.id.tv_rvitem_myrides_action)
    }
}