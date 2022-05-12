package com.goby24.goby24.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goby24.goby24.R
import com.goby24.goby24.models.TouristPackageRequestFromTouristModel

class AdapterTouristPackageRequestFromTouristRvItem(listdata: ArrayList<TouristPackageRequestFromTouristModel>, ctx: Context):
    RecyclerView.Adapter<AdapterTouristPackageRequestFromTouristRvItem.ViewHolder>() {
    private var listdata: ArrayList<TouristPackageRequestFromTouristModel> = listdata
    private var mContext: Context = ctx
    private var mArrExpandStatus:ArrayList<Boolean> = ArrayList()

    fun setData(ctx:Context, data:ArrayList<TouristPackageRequestFromTouristModel>){
        mContext = ctx;
        listdata = data
        for (i in 0 until listdata.size){
            mArrExpandStatus.add(false)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.rvitem_tourist_package_all_request, parent, false)
        return ViewHolder(listItem)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData: TouristPackageRequestFromTouristModel = listdata[position]
        holder.tvDate.text = itemData.journey_start_date + "-" + itemData.journey_end_date
        if (itemData.touristspot != null){
            holder.tvTouristSpotName.text = itemData.touristspot.spot_name
            holder.tvDescription.text = itemData.touristspot.spot_brief
        }else{
            holder.tvTouristSpotName.text = itemData.touristspot.spot_name
            holder.tvDescription.text = itemData.touristspot.spot_brief
        }
        holder.tvLocation.text = itemData.journey_from + " - " + itemData.journey_to
        holder.tvPrice.text = itemData.package_budget + itemData.currency
        holder.tvStatus.text = itemData.request_status

        if (mArrExpandStatus[position]){
            holder.llDetailRoot.visibility = View.VISIBLE
            holder.ivExpand.setImageResource(R.drawable.down_arrow_white)
        }else{
            holder.llDetailRoot.visibility = View.GONE
            holder.ivExpand.setImageResource(R.drawable.right_arrow_white)
        }

        holder.ivExpand.setOnClickListener{
            mArrExpandStatus[position] = !mArrExpandStatus[position]
            notifyDataSetChanged()
        }

        holder.tvActionContact.setOnClickListener{

        }

        holder.tvActionAccept.setOnClickListener{

        }
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var llRoot: View = itemView.findViewById(R.id.ll_rvitem_tourist_package_all_request_root)
        var llActionRoot:View = itemView.findViewById(R.id.ll_rvitem_tourist_package_all_request_actions_root)
        var llDetailRoot:View = itemView.findViewById(R.id.ll_rvitem_tourist_package_all_request_detail_root)
        var ivExpand:ImageView = itemView.findViewById(R.id.iv_rvitem_tourist_package_all_request_expand)
        var tvDate:TextView = itemView.findViewById(R.id.tv_rvitem_tourist_package_all_request_date_value)
        var tvTouristSpotName:TextView = itemView.findViewById(R.id.tv_rvitem_tourist_package_all_request_tourist_spot_value)
        var tvDescription:TextView = itemView.findViewById(R.id.tv_rvitem_tourist_package_all_request_detail_description_value)
        var tvLocation:TextView = itemView.findViewById(R.id.tv_rvitem_tourist_package_all_request_detail_location_value)
        var tvPrice:TextView = itemView.findViewById(R.id.tv_rvitem_tourist_package_all_request_detail_price_value)
        var tvStatus:TextView = itemView.findViewById(R.id.tv_rvitem_tourist_package_all_request_detail_status_value)
        var tvActionContact:TextView = itemView.findViewById(R.id.tv_rvitem_tourist_package_all_request_detail_action_contact)
        var tvActionAccept:TextView = itemView.findViewById(R.id.tv_rvitem_tourist_package_all_request_detail_action_accept)
    }
}