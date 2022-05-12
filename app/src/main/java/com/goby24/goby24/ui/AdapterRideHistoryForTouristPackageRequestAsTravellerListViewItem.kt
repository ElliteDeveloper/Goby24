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
import com.goby24.goby24.models.RideDataForTouristPackageRequestAsRiderModel
import com.goby24.goby24.models.RideDataForTouristPackageRequestAsTravellerModel

class AdapterRideHistoryForTouristPackageRequestAsTravellerListViewItem(context:Context):BaseAdapter() {
    private val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var mContext:Context = context
    private var mArrData:ArrayList<RideDataForTouristPackageRequestAsTravellerModel> = ArrayList()

    fun setData(context: Context, arrData:ArrayList<RideDataForTouristPackageRequestAsTravellerModel>) {
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
            view = mInflater.inflate(R.layout.rvitem_rides_tourist_package_request_as_traveller, null)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }

        val item: RideDataForTouristPackageRequestAsTravellerModel = mArrData[position]
        vh.mTvDateTime.text = item.journey_start_date + " - " + item.journey_end_date
        vh.mTvStatus.text = item.request_status
        if (item.touristspot.spot_name != "null"){
            vh.mTvAddressStart.text = item.touristspot.spot_name
        }else{
            vh.mTvAddressStart.text = ""
        }
        vh.mTvCost.text = item.currency + " " + item.package_budget
        return view
    }

    private class ItemHolder(row: View) {
        var mTvDateTime: TextView = row.findViewById(R.id.tv_rvitem_myrides_datetime)
        var mTvStatus: TextView = row.findViewById(R.id.tv_rvitem_myrides_status)
        var mTvAddressStart: TextView = row.findViewById(R.id.tv_rvitem_myrides_address_start)
        var mTvCost: TextView = row.findViewById(R.id.tv_rvitem_myrides_cost)
    }
}