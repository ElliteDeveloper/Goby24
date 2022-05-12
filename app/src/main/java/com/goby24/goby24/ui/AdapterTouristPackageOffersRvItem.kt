package com.goby24.goby24.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goby24.goby24.MainActivity
import com.goby24.goby24.R
import com.goby24.goby24.fragments.FragTouristPackageOfferDetail
import com.goby24.goby24.models.TouristPackageOfferModel
import com.squareup.picasso.Picasso

class AdapterTouristPackageOffersRvItem(listdata: ArrayList<TouristPackageOfferModel>, ctx: Context):
    RecyclerView.Adapter<AdapterTouristPackageOffersRvItem.ViewHolder>() {
    private var listdata: ArrayList<TouristPackageOfferModel> = listdata
    private var mContext: Context = ctx
    private val mStrRiderTypeRider = ctx.getString(R.string.rider_type_rider)
    private val mStrRiderTypeGuide = ctx.getString(R.string.rider_type_guide)

    fun setData(ctx:Context, data:ArrayList<TouristPackageOfferModel>){
        mContext = ctx;
        listdata = data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.gvitem_tourist_package, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData: TouristPackageOfferModel = listdata[position]
        Picasso.get()
            .load(itemData.package_cover)
            .fit().centerCrop()
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo)
            .into(holder.ivBannerImage)
        holder.tvDates.text = String.format(mContext.getString(R.string.days_format_string), itemData.no_of_days)
        holder.tvName.text = itemData.package_name
        holder.tvPrice.text = itemData.package_price + itemData.currency
        if (itemData.is_guide){
            holder.tvRiderType.text = mStrRiderTypeGuide
        }else{
            holder.tvRiderType.text = mStrRiderTypeRider
        }

        holder.llRoot.setOnClickListener {
            if (mContext is MainActivity){
                (mContext as MainActivity).addFrag(FragTouristPackageOfferDetail.newInstance(itemData), true, true)
            }
        }
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var llRoot: View = itemView.findViewById(R.id.rl_gvitem_tourist_package_offer_root)
        var ivBannerImage:ImageView = itemView.findViewById(R.id.riv_gvitem_tourist_package)
        var tvDates:TextView = itemView.findViewById(R.id.tv_gvitem_tourist_package_date)
        var tvName:TextView = itemView.findViewById(R.id.tv_gvitem_tourist_package_name)
        var tvPrice:TextView = itemView.findViewById(R.id.tv_gvitem_tourist_package_price)
        var tvRiderType:TextView = itemView.findViewById(R.id.tv_gvitem_tourist_package_rider_type)
    }
}