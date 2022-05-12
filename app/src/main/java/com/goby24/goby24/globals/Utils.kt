package com.goby24.goby24.globals

import android.content.Context
import android.view.View
import com.goby24.goby24.R
import java.text.SimpleDateFormat
import java.util.*
import android.view.ViewGroup

import android.view.View.MeasureSpec
import android.widget.ListAdapter
import android.widget.ListView


class Utils {
    companion object {
        fun getAgeString(ctx: Context, strBirthday:String?):String{
            try {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val dob: Date = dateFormat.parse(strBirthday)
                val cal1 = Calendar.getInstance()
                cal1.time = dob
                val cal: Calendar = Calendar.getInstance()
                val age = cal.get(Calendar.YEAR) - cal1.get(Calendar.YEAR)
                return age.toString() + " " + ctx.getString(R.string.age_suffix)
            }catch (e:Exception) {
                return "0 " + ctx.getString(R.string.age_suffix)
            }
        }

        fun setListViewHeightBasedOnChildren(listView: ListView) {
            val listAdapter: ListAdapter = listView.adapter
                ?: // pre-condition
                return
            var totalHeight = 0
            val desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST)
            for (i in 0 until listAdapter.count) {
                val listItem: View = listAdapter.getView(i, null, listView)
                listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED)
                totalHeight += listItem.measuredHeight
            }
            val params: ViewGroup.LayoutParams = listView.layoutParams
            params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
            listView.layoutParams = params
            listView.requestLayout()
        }
    }
}