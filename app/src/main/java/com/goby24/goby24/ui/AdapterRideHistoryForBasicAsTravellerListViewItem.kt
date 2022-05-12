package com.goby24.goby24.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.goby24.goby24.R
import com.goby24.goby24.models.RideDataForBasicRideAsTravellerModel

class AdapterRideHistoryForBasicAsTravellerListViewItem(context: Context) : BaseAdapter() {
    private val mInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var mContext: Context = context
    private var mArrData: ArrayList<RideDataForBasicRideAsTravellerModel> = ArrayList()
    private var mStrForAction = mContext.getString(R.string.upcoming)
    private var mStrAction = mContext.getString(R.string.ride_action_cancel)

    fun setData(context: Context, arrData: ArrayList<RideDataForBasicRideAsTravellerModel>) {
        mContext = context
        mArrData = ArrayList()
        if (arrData.size > 0) {
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
        val vh: ItemHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.rvitem_myrides, null)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }

        val item: RideDataForBasicRideAsTravellerModel = mArrData[position]
        vh.mTvDateTime.text = item.date + " " + item.time
        vh.mTvStatus.text = item.rideStatus
        if (item.route != null) {
            if (item.route.size > 0) {
                vh.mTvAddressStart.text = item.route[0]
                vh.mTvAddressEnd.text = item.route[item.route.size - 1]
            } else {
                vh.mTvAddressStart.text = ""
                vh.mTvAddressEnd.text = ""
            }
        } else {
            vh.mTvAddressStart.text = ""
            vh.mTvAddressEnd.text = ""
        }

        vh.mTvDuration.text = item.journeyTime
        vh.mTvDistance.text = item.distance
        vh.mTvCost.text = item.currency + " " + item.fare
        if (item.rideStatus.contains(mStrForAction, true)) {
            vh.mTvAction.visibility = View.VISIBLE
            vh.mTvAction.text = mStrAction
        } else {
            vh.mTvAction.visibility = View.GONE
        }
        vh.mTvAction.setOnClickListener {

        }
        return view
    }

    private class ItemHolder(row: View) {
        var mTvDateTime: TextView = row.findViewById(R.id.tv_rvitem_myrides_datetime)
        var mTvStatus: TextView = row.findViewById(R.id.tv_rvitem_myrides_status)
        var mTvAddressStart: TextView = row.findViewById(R.id.tv_rvitem_myrides_address_start)
        var mTvAddressEnd: TextView = row.findViewById(R.id.tv_rvitem_myrides_address_end)
        var mTvDuration: TextView = row.findViewById(R.id.tv_rvitem_myrides_duration)
        var mTvDistance: TextView = row.findViewById(R.id.tv_rvitem_myrides_distance)
        var mTvCost: TextView = row.findViewById(R.id.tv_rvitem_myrides_cost)
        var mTvAction: TextView = row.findViewById(R.id.tv_rvitem_myrides_action)
    }
}