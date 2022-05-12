package com.goby24.goby24.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.goby24.goby24.R
import com.goby24.goby24.models.RideDataForBasicRideAsTravellerModel
import com.goby24.goby24.models.RideDataForRideRequestAsRiderModel
import com.goby24.goby24.models.RideDataForRideRequestAsTravellerModel

class AdapterRideHistoryForRideRequestAsRiderListViewItem(context:Context):BaseAdapter() {
    private val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var mContext:Context = context
    private var mArrData:ArrayList<RideDataForRideRequestAsRiderModel> = ArrayList()

    fun setData(context: Context, arrData:ArrayList<RideDataForRideRequestAsRiderModel>) {
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
            view = mInflater.inflate(R.layout.rvitem_rides_ride_request_as_traveller, null)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }

        val item: RideDataForRideRequestAsRiderModel = mArrData[position]
        vh.mTvDateTime.text = item.date
        vh.mTvStatus.text = item.request_status
        if (item.journey_from != "null"){
            vh.mTvAddressStart.text = item.journey_from
        }else{
            vh.mTvAddressStart.text = ""
        }

        if (item.journey_to != "null"){
            vh.mTvAddressEnd.text = item.journey_to
        }else{
            vh.mTvAddressEnd.text = ""
        }
        vh.mTvDuration.text = item.time

        vh.mTvCost.text = item.currency + " " + item.fare
        if (item.request_status.contains("upcoming", true)){
            vh.mTvAction.visibility = View.VISIBLE
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
        var mTvAddressEnd: TextView = row.findViewById(R.id.tv_rvitem_myrides_address_end)
        var mTvDuration: TextView = row.findViewById(R.id.tv_rvitem_myrides_duration)
        var mTvCost: TextView = row.findViewById(R.id.tv_rvitem_myrides_cost)
        var mTvAction: TextView = row.findViewById(R.id.tv_rvitem_myrides_action)
    }
}