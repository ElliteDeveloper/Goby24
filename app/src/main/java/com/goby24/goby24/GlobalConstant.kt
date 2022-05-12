package com.goby24.goby24

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.goby24.goby24.models.UserProfileModel
import com.goby24.goby24.ui.OnPaginatorPageNumberClickedListener
import java.util.regex.Pattern


class GlobalConstant {
    companion object {
        private val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )

        lateinit var globalMyProfile:UserProfileModel

        public fun checkEmail(email: String): Boolean {
            return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
        }

        fun relocatePageNumbersInPaginator(
            llParent: LinearLayout,
            nCurPageNum: Int,
            nTotalItems: Int,
            nItemsPerPage: Int,
            ctx: Context,
            listener: OnPaginatorPageNumberClickedListener
        ) {
            llParent.removeAllViews()
            val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var nTotalPages = 0
            nTotalPages = if (nTotalItems % nItemsPerPage == 0) {
                (nTotalItems / nItemsPerPage)
            } else {
                (nTotalItems / nItemsPerPage) + 1
            }
            var nMinPageNum = nCurPageNum - 1
            var nMaxPageNum = nCurPageNum + 2

            while (nMinPageNum < 1){
                nMinPageNum++
                nMaxPageNum++
            }
            while(nMaxPageNum > nTotalPages){
                nMaxPageNum--
                if (nMinPageNum > 1) nMinPageNum--
            }

            for (iPageIndex in nMinPageNum until nMaxPageNum + 1) {
                if (iPageIndex == nCurPageNum) {
                    val pageNumberView = inflater.inflate(R.layout.tv_pf_page_number_active, null)
                    pageNumberView.findViewById<TextView>(R.id.tv_pf_pagenum).text = String.format("%d", iPageIndex)
                    pageNumberView.setOnClickListener {
                        listener.onPageNumberClicked(iPageIndex)
                    }
                    llParent.addView(pageNumberView)
                } else {
                    val pageNumberView = inflater.inflate(R.layout.tv_pf_page_number_inactive, null)
                    pageNumberView.findViewById<TextView>(R.id.tv_pf_pagenum).text = String.format("%d", iPageIndex)
                    pageNumberView.setOnClickListener {
                        listener.onPageNumberClicked(iPageIndex)
                    }
                    llParent.addView(pageNumberView)
                }
            }
        }

        fun relocatePageNumbersInPaginator(
            llParent: LinearLayout,
            nCurPageNum: Int,
            nTotalPages: Int,
            ctx: Context,
            listener: OnPaginatorPageNumberClickedListener
        ) {
            llParent.removeAllViews()
            val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var nMinPageNum = nCurPageNum - 1
            var nMaxPageNum = nCurPageNum + 2

            while (nMinPageNum < 1){
                nMinPageNum++
                nMaxPageNum++
            }
            while(nMaxPageNum > nTotalPages){
                nMaxPageNum--
                if (nMinPageNum > 1) nMinPageNum--
            }

            for (iPageIndex in nMinPageNum until nMaxPageNum + 1) {
                if (iPageIndex == nCurPageNum) {
                    val pageNumberView = inflater.inflate(R.layout.tv_pf_page_number_active, null)
                    pageNumberView.findViewById<TextView>(R.id.tv_pf_pagenum).text = String.format("%d", iPageIndex)
                    pageNumberView.setOnClickListener {
                        listener.onPageNumberClicked(iPageIndex)
                    }
                    llParent.addView(pageNumberView)
                } else {
                    val pageNumberView = inflater.inflate(R.layout.tv_pf_page_number_inactive, null)
                    pageNumberView.findViewById<TextView>(R.id.tv_pf_pagenum).text = String.format("%d", iPageIndex)
                    pageNumberView.setOnClickListener {
                        listener.onPageNumberClicked(iPageIndex)
                    }
                    llParent.addView(pageNumberView)
                }
            }
        }
    }
}