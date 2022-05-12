package com.goby24.goby24.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.goby24.goby24.GlobalConstant
import com.goby24.goby24.MainActivity
import com.goby24.goby24.R
import com.goby24.goby24.models.RatingReview
import com.goby24.goby24.ui.AdapterReviewListViewItem
import com.goby24.goby24.ui.OnPaginatorPageNumberClickedListener
import com.goby24.goby24.ui.SpinnerAdapterPhonePrefix

class FragRatingsListView: Fragment() {

    private lateinit var mRootView:View
    private lateinit var mTvRatingScore: TextView
    private lateinit var mArrData:ArrayList<RatingReview>
    private lateinit var mArrPageData:ArrayList<RatingReview>
    private lateinit var mLvData:ListView
    private lateinit var mLlPaginatorPrev:View
    private lateinit var mLlPaginatorNext:View
    private lateinit var mLlPaginatorPages: LinearLayout
    private lateinit var mTvPages:TextView
    private var mItemsPerPage = 9
    private var mCurPageNum = 1
    private var mTotalPages:Int = 0
    private var mListener = object: OnPaginatorPageNumberClickedListener(){
        override fun onPageNumberClicked(iPageNumber: Int) {
            if (mCurPageNum == iPageNumber) return
            mCurPageNum = iPageNumber
            relocateData(mCurPageNum)
        }
    }

    private lateinit var mAdapterLvData:AdapterReviewListViewItem

    companion object {
        fun newInstance(): FragRatingsListView {
            return FragRatingsListView()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(R.layout.frag_ratings_list, container, false)
        initData()
        initView()
        return mRootView
    }

    private fun initData(){
        mArrData = ArrayList()
        mArrPageData = ArrayList()
        for (i in 0 until 160){
            val item = RatingReview("Lorem Ipsum--$i", "Excellent", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempou")
            mArrData.add(item)
        }

        mTotalPages = if (mArrData.size % mItemsPerPage == 0) {
            (mArrData.size / mItemsPerPage)
        } else {
            (mArrData.size / mItemsPerPage) + 1
        }
    }

    private fun initView(){
        mLvData = mRootView.findViewById(R.id.lv_frag_ratings_list_data)
        mTvRatingScore = mRootView.findViewById(R.id.tv_frag_ratings_list_rating)
        mLlPaginatorPrev = mRootView.findViewById(R.id.ll_pf_previous)
        mLlPaginatorNext = mRootView.findViewById(R.id.ll_pf_next)
        mLlPaginatorPages = mRootView.findViewById(R.id.ll_pf_page_numbers)

        GlobalConstant.relocatePageNumbersInPaginator(
            mLlPaginatorPages.findViewById(R.id.ll_pf_page_numbers),
            mCurPageNum,
            mArrData.size,
            mItemsPerPage,
            requireActivity(),
            mListener
        )
        mRootView.findViewById<View>(R.id.ll_pf_previous).setOnClickListener {
            if (mCurPageNum == 1) return@setOnClickListener;
            mCurPageNum--
            relocateData(mCurPageNum)
        }

        mRootView.findViewById<View>(R.id.ll_pf_next).setOnClickListener {
            if (mCurPageNum == mTotalPages) return@setOnClickListener
            mCurPageNum++
            relocateData(mCurPageNum)
        }

        mTvPages = mRootView.findViewById(R.id.tv_pf_msg)

        mRootView.findViewById<View>(R.id.iv_frag_ratings_list_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        mAdapterLvData = AdapterReviewListViewItem(requireContext())
        mLvData.adapter = mAdapterLvData
        relocateData(mCurPageNum)

        mLvData.setOnItemClickListener { adapterView, view, i, l ->
            if (requireActivity() is MainActivity) {
                val data:RatingReview = mArrPageData[i]
                (requireActivity() as MainActivity).addFrag(FragRatingDetail.newInstance(data), true, true)
            }
        }
    }

    fun relocateData(iPageIndex:Int){
        mArrPageData.clear()
        var iStartIndex:Int = (iPageIndex - 1) * mItemsPerPage
        var iEndIndex:Int = iPageIndex * mItemsPerPage - 1
        if (iStartIndex < 0) iStartIndex = 0
        if (iEndIndex >= mArrData.size) iEndIndex = mArrData.size - 1
        for (i in iStartIndex until iEndIndex + 1){
            mArrPageData.add(mArrData[i])
        }
        GlobalConstant.relocatePageNumbersInPaginator(
            mLlPaginatorPages.findViewById(R.id.ll_pf_page_numbers),
            mCurPageNum,
            mArrData.size,
            mItemsPerPage,
            requireActivity(),
            mListener
        )
        mAdapterLvData.setData(requireActivity(), mArrPageData)
        (mLvData.adapter as BaseAdapter).notifyDataSetChanged()
    }
}