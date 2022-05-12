package com.goby24.goby24.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.goby24.goby24.GlobalConstant
import com.goby24.goby24.R
import com.goby24.goby24.globals.ApiInterface
import com.goby24.goby24.globals.RetrofitInstance
import com.goby24.goby24.models.*
import com.goby24.goby24.ui.*
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

@Suppress("DEPRECATION")
class FragTouristPackagesMain: Fragment() {

    private lateinit var mRootView:View
    private lateinit var mLlPaginationPrevPackageOffers:View
    private lateinit var mLlPaginationNextPackageOffers:View
    private lateinit var mLlPaginationPagesPackageOffers: LinearLayout
    private lateinit var mLlPaginationPackageOffersRoot:View
    private lateinit var mTvPagesPackageOffers:TextView

    private lateinit var mLlPaginationPrevRequestFromTourists:View
    private lateinit var mLlPaginationNextRequestFromTourists:View
    private lateinit var mLlPaginationPagesRequestFromTourists: LinearLayout
    private lateinit var mLlPaginationRequestFromTouristsRoot:View
    private lateinit var mTvPagesTouristPackageRequestFromTourists:TextView

    private lateinit var mArrPackageOffers:ArrayList<TouristPackageOfferModel>
    private lateinit var mArrPackageOffersPageData:ArrayList<TouristPackageOfferModel>
    private lateinit var mAdapterRvTouristPackageOffers:AdapterTouristPackageOffersRvItem
    private lateinit var mRvTouristPackageOffers:RecyclerView

    private lateinit var mArrTouristPackageRequestFromTourists:ArrayList<TouristPackageRequestFromTouristModel>
    private lateinit var mArrTouristPackageRequestFromTouristsPageData:ArrayList<TouristPackageRequestFromTouristModel>
    private lateinit var mAdapterRvTouristPackageRequestFromTourists:AdapterTouristPackageRequestFromTouristLvItem
    private lateinit var mRvTouristPackageRequestFromTourists:ListView


    private lateinit var mTlMainHeader:TabLayout
    private lateinit var mTlSubHeader:TabLayout
    private lateinit var mLlPackageOffersContentRoot:View
    private lateinit var mLlRequestFromTouristContentRoot:View
    private lateinit var mSvOfferPackageContentRoot:View

    private lateinit var mSvRequestFromTouristCreateContent:View
    private lateinit var mLlRequestFromTouristAllRequestsContent:View
    private lateinit var mRlSearchRoot:View

    private var mItemsPerPage = 6
    private var mCurPageNumInPackageOffers = 1
    private var mCurPageNumInTouristPackageRequestFromTourists = 1

    private var mTotalItemsInPackageOffers = 0
    private var mTotalPagesTouristPackageRequestFromTourists = 0

    private var mTotalPagesPackageOffers:Int = 0
    private var mTotalItemsInTouristPackageRequestFromTourists:Int = 0

    private var mListenerPackageOffers = object: OnPaginatorPageNumberClickedListener(){
        override fun onPageNumberClicked(iPageNumber: Int) {
            if (mCurPageNumInPackageOffers == iPageNumber) return
            mCurPageNumInPackageOffers = iPageNumber
            relocateDataInPackageOffers()
        }
    }

    private var mListenerTouristPackageRequestFromTourists = object: OnPaginatorPageNumberClickedListener(){
        override fun onPageNumberClicked(iPageNumber: Int) {
            if (mCurPageNumInTouristPackageRequestFromTourists == iPageNumber) return
            mCurPageNumInTouristPackageRequestFromTourists = iPageNumber
            relocateDataInTouristPackageRequestFromTourists()
        }
    }


    companion object {
        private var mMainTabSelectedIndex:Int = 0
        private var mSubTabSelectedIndex:Int = 0
        private var mIsLoadedPackageOffers = false
        private var mProgressPackageOffers = false
        private var mIsLoadedTouristPackageRequestFromTourists = false
        private var mProgressTouristPackageRequestFromTourists = false

        fun newInstance(): FragTouristPackagesMain {
            mMainTabSelectedIndex = 0
            mSubTabSelectedIndex = 0
            mIsLoadedPackageOffers = false
            mIsLoadedTouristPackageRequestFromTourists = false

            mProgressPackageOffers = false
            mProgressTouristPackageRequestFromTourists = false
            return FragTouristPackagesMain()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(R.layout.frag_tourist_package_main, container, false)
        initData()
        initView()
        return mRootView
    }

    private fun initData(){
        if (!mIsLoadedPackageOffers) {
            mArrPackageOffers = ArrayList()
            mArrPackageOffersPageData = ArrayList()
        }
        if (!mIsLoadedTouristPackageRequestFromTourists) {
            mArrTouristPackageRequestFromTourists = ArrayList()
            mArrTouristPackageRequestFromTouristsPageData = ArrayList()
        }
    }

    private fun reloadDataForPackageOffers(){
        val progressDialog = ProgressDialog(requireActivity())
        if (!mProgressPackageOffers) {
            progressDialog.show()
        }

        mProgressPackageOffers = true
        mArrPackageOffers = ArrayList()
        mArrPackageOffersPageData = ArrayList()

        val retIn = RetrofitInstance.getRetrofitInstanceWithAccessTokenAndWithoutBody().create(ApiInterface::class.java)

        retIn.getTouristPackageOffers(mCurPageNumInPackageOffers, mItemsPerPage).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                if (progressDialog.isShowing)
                    progressDialog.hide()
                Toast.makeText(requireActivity(), t.message, Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try{
                    val strResponseBody: String = response.body()!!.string()
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            var jsonObject = JSONObject(strResponseBody)
                            if (jsonObject.has("result")){
                                if (jsonObject.get("result") is JSONArray){
                                    val jsonArray = jsonObject.get("result") as JSONArray
                                    val gson = Gson()
                                    val userListType: Type = object : TypeToken<ArrayList<TouristPackageOfferModel?>?>() {}.type

                                    mArrPackageOffers = gson.fromJson(jsonArray.toString(), userListType)
                                    mIsLoadedPackageOffers = true
                                    mTotalPagesPackageOffers = 1
                                    mTotalItemsInPackageOffers = mArrPackageOffers.size
                                    relocateDataInPackageOffers()
                                }else{
                                    jsonObject = jsonObject.getJSONObject("result")
                                    val jsonArray = jsonObject.get("results") as JSONArray
                                    val gson = Gson()
                                    val userListType: Type = object : TypeToken<ArrayList<TouristPackageOfferModel?>?>() {}.type
                                    mTotalItemsInPackageOffers = jsonObject.optInt("count", 0)
                                    mArrPackageOffers = gson.fromJson(jsonArray.toString(), userListType)
                                    mTotalPagesPackageOffers = if (mArrPackageOffers.size == 0){
                                        0
                                    }else{
                                        (mTotalItemsInPackageOffers + mItemsPerPage - 1) / mItemsPerPage
                                    }
                                    mIsLoadedPackageOffers = true
                                    relocateDataInPackageOffers()
                                }
                            }
                        }else{
                            Toast.makeText(requireActivity(), R.string.global_failure, Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(requireActivity(), R.string.global_failure, Toast.LENGTH_SHORT).show()
                    }
                    if (progressDialog.isShowing)
                        progressDialog.hide()
                }catch(e:Exception){
                    if (progressDialog.isShowing) progressDialog.hide()
                    Toast.makeText(requireActivity(), R.string.global_failure, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun reloadDataForTouristPackageRequestFromTourists(){
        val progressDialog = ProgressDialog(requireActivity())
        if (!mProgressTouristPackageRequestFromTourists) {
            progressDialog.show()
        }

        mProgressTouristPackageRequestFromTourists = true
        mArrTouristPackageRequestFromTourists = ArrayList()
        mArrTouristPackageRequestFromTouristsPageData = ArrayList()

        val retIn = RetrofitInstance.getRetrofitInstanceWithAccessTokenAndWithoutBody().create(ApiInterface::class.java)

        retIn.getTouristPackageAllRequestsFromTourists(mCurPageNumInTouristPackageRequestFromTourists, mItemsPerPage).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                if (progressDialog.isShowing)
                    progressDialog.hide()
                Toast.makeText(requireActivity(), t.message, Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try{
                    val strResponseBody: String = response.body()!!.string()
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            var jsonObject = JSONObject(strResponseBody)
                            if (jsonObject.has("result")){
                                if (jsonObject.get("result") is JSONArray){
                                    val jsonArray = jsonObject.get("result") as JSONArray
                                    val gson = Gson()
                                    val userListType: Type = object : TypeToken<ArrayList<TouristPackageRequestFromTouristModel?>?>() {}.type

                                    mArrTouristPackageRequestFromTourists = gson.fromJson(jsonArray.toString(), userListType)
                                    mIsLoadedTouristPackageRequestFromTourists = true
                                    mTotalPagesTouristPackageRequestFromTourists = 1
                                    mTotalItemsInTouristPackageRequestFromTourists = mArrTouristPackageRequestFromTourists.size
                                    relocateDataInTouristPackageRequestFromTourists()
                                }else{
                                    jsonObject = jsonObject.getJSONObject("result")
                                    val jsonArray = jsonObject.get("results") as JSONArray
                                    val gson = Gson()
                                    val userListType: Type = object : TypeToken<ArrayList<TouristPackageRequestFromTouristModel?>?>() {}.type
                                    mTotalItemsInTouristPackageRequestFromTourists = jsonObject.optInt("count", 0)
                                    mArrTouristPackageRequestFromTourists = gson.fromJson(jsonArray.toString(), userListType)
                                    mTotalPagesTouristPackageRequestFromTourists = if (mArrTouristPackageRequestFromTourists.size == 0){
                                        0
                                    }else{
                                        (mTotalItemsInTouristPackageRequestFromTourists + mItemsPerPage - 1) / mItemsPerPage
                                    }
                                    mIsLoadedTouristPackageRequestFromTourists = true
                                    relocateDataInTouristPackageRequestFromTourists()
                                }
                            }
                        }else{
                            Toast.makeText(requireActivity(), R.string.global_failure, Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(requireActivity(), R.string.global_failure, Toast.LENGTH_SHORT).show()
                    }
                    if (progressDialog.isShowing)
                        progressDialog.hide()
                }catch(e:Exception){
                    if (progressDialog.isShowing) progressDialog.hide()
                    Toast.makeText(requireActivity(), R.string.global_failure, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTlMainHeader.selectTab(mTlMainHeader.getTabAt(mMainTabSelectedIndex))
        mTlSubHeader.selectTab(mTlSubHeader.getTabAt(mSubTabSelectedIndex))
    }

    private fun initView(){

        mTlMainHeader = mRootView.findViewById(R.id.tl_frag_tourist_package_main)
        mTlSubHeader = mRootView.findViewById(R.id.tl_frag_tourist_package_main_request_from_tourists)

        mLlPaginationPrevPackageOffers = mRootView.findViewById(R.id.ll_pf_previous1)
        mLlPaginationNextPackageOffers = mRootView.findViewById(R.id.ll_pf_next1)
        mLlPaginationPagesPackageOffers = mRootView.findViewById(R.id.ll_pf_page_numbers1)
        mLlPaginationPackageOffersRoot = mRootView.findViewById(R.id.ll_frag_tourist_package_package_offers_footer_navigation_root)

        mLlPaginationPrevRequestFromTourists = mRootView.findViewById(R.id.ll_pf_previous2)
        mLlPaginationNextRequestFromTourists = mRootView.findViewById(R.id.ll_pf_next2)
        mLlPaginationPagesRequestFromTourists = mRootView.findViewById(R.id.ll_pf_page_numbers2)
        mLlPaginationRequestFromTouristsRoot = mRootView.findViewById(R.id.ll_frag_tourist_package_all_requests_footer_navigation_root)

        mLlPackageOffersContentRoot = mRootView.findViewById(R.id.rl_frag_tourist_package_main_tourist_package_offers_content)
        mLlRequestFromTouristContentRoot = mRootView.findViewById(R.id.ll_frag_tourist_package_main_request_from_tourists_content_root)
        mSvOfferPackageContentRoot = mRootView.findViewById(R.id.sv_frag_tourist_package_main_offer_package_content_root)

        mSvRequestFromTouristCreateContent = mRootView.findViewById(R.id.sv_frag_tourist_package_main_create_content_root)
        mLlRequestFromTouristAllRequestsContent = mRootView.findViewById(R.id.ll_frag_tourist_package_main_all_request_root)

        mRvTouristPackageOffers = mRootView.findViewById(R.id.rv_frag_tourist_package_offers)
        mRvTouristPackageOffers.layoutManager = GridLayoutManager(requireActivity(), 2)

        mRvTouristPackageRequestFromTourists = mRootView.findViewById(R.id.lv_frag_tourist_package_all_requests)
//        mRvTouristPackageRequestFromTourists.layoutManager = LinearLayoutManager(requireActivity())

        mRlSearchRoot = mRootView.findViewById(R.id.rl_frag_tourist_package_main_search_root)

        mAdapterRvTouristPackageOffers = AdapterTouristPackageOffersRvItem(mArrPackageOffersPageData, requireActivity())
        mAdapterRvTouristPackageRequestFromTourists = AdapterTouristPackageRequestFromTouristLvItem(mArrTouristPackageRequestFromTourists, requireActivity(), mRvTouristPackageRequestFromTourists)
        when(mMainTabSelectedIndex){
            0->{
                mLlPackageOffersContentRoot.visibility = View.VISIBLE
                mLlRequestFromTouristContentRoot.visibility = View.GONE
                mSvOfferPackageContentRoot.visibility = View.GONE
                GlobalConstant.relocatePageNumbersInPaginator(
                    mLlPaginationPagesPackageOffers.findViewById(R.id.ll_pf_page_numbers1),
                    mCurPageNumInPackageOffers,
                    mArrPackageOffers.size,
                    mItemsPerPage,
                    requireActivity(),
                    mListenerPackageOffers
                )
                relocateDataInPackageOffers()
                if (mArrPackageOffers.size < mItemsPerPage){
                    mLlPaginationPackageOffersRoot.visibility = View.GONE
                }else{
                    mLlPaginationPackageOffersRoot.visibility = View.VISIBLE
                }
                mRlSearchRoot.visibility = View.VISIBLE
            }
            1->{
                mLlPackageOffersContentRoot.visibility = View.GONE
                mLlRequestFromTouristContentRoot.visibility = View.VISIBLE
                mSvOfferPackageContentRoot.visibility = View.GONE
                if (mSubTabSelectedIndex == 0){
                    mSvRequestFromTouristCreateContent.visibility = View.VISIBLE
                    mLlRequestFromTouristAllRequestsContent.visibility = View.GONE
                    mRlSearchRoot.visibility = View.INVISIBLE
                }else{
                    mSvRequestFromTouristCreateContent.visibility = View.GONE
                    mLlRequestFromTouristAllRequestsContent.visibility = View.VISIBLE
                    mRlSearchRoot.visibility = View.VISIBLE
                    relocateDataInTouristPackageRequestFromTourists()
                    if (mArrTouristPackageRequestFromTourists.size < mItemsPerPage){
                        mLlPaginationRequestFromTouristsRoot.visibility = View.GONE
                    }else{
                        mLlPaginationRequestFromTouristsRoot.visibility = View.VISIBLE
                    }
                }
            }
            2->{
                mLlPackageOffersContentRoot.visibility = View.GONE
                mLlRequestFromTouristContentRoot.visibility = View.GONE
                mSvOfferPackageContentRoot.visibility = View.VISIBLE
                mRlSearchRoot.visibility = View.INVISIBLE
            }
        }

        mRootView.findViewById<View>(R.id.ll_pf_previous1).setOnClickListener {
            if (mCurPageNumInPackageOffers == 1) return@setOnClickListener
            mCurPageNumInPackageOffers--
            relocateDataInPackageOffers()
        }

        mRootView.findViewById<View>(R.id.ll_pf_next1).setOnClickListener {
            if (mCurPageNumInPackageOffers == mTotalPagesPackageOffers) return@setOnClickListener
            mCurPageNumInPackageOffers++
            relocateDataInPackageOffers()
        }

        mTvPagesPackageOffers = mRootView.findViewById(R.id.tv_pf_msg1)

        mRootView.findViewById<View>(R.id.ll_pf_previous2).setOnClickListener {
            if (mCurPageNumInTouristPackageRequestFromTourists == 1) return@setOnClickListener
            mCurPageNumInTouristPackageRequestFromTourists--
            relocateDataInTouristPackageRequestFromTourists()
        }

        mRootView.findViewById<View>(R.id.ll_pf_next2).setOnClickListener {
            if (mCurPageNumInTouristPackageRequestFromTourists == mTotalPagesTouristPackageRequestFromTourists) return@setOnClickListener
            mCurPageNumInTouristPackageRequestFromTourists++
            relocateDataInTouristPackageRequestFromTourists()
        }
        mTvPagesTouristPackageRequestFromTourists = mRootView.findViewById(R.id.tv_pf_msg2)

        mTlMainHeader.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mMainTabSelectedIndex = mTlMainHeader.selectedTabPosition
                handleTabSelectChange()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })

        mTlSubHeader.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mSubTabSelectedIndex = mTlSubHeader.selectedTabPosition
                handleTabSelectChange()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })
    }

    fun handleTabSelectChange(){
        when(mMainTabSelectedIndex){
            0->{
                mLlPackageOffersContentRoot.visibility = View.VISIBLE
                mLlRequestFromTouristContentRoot.visibility = View.GONE
                mSvOfferPackageContentRoot.visibility = View.GONE
                mRlSearchRoot.visibility = View.VISIBLE
            }
            1->{
                mLlPackageOffersContentRoot.visibility = View.GONE
                mLlRequestFromTouristContentRoot.visibility = View.VISIBLE
                mSvOfferPackageContentRoot.visibility = View.GONE
                if (mSubTabSelectedIndex == 0){
                    mSvRequestFromTouristCreateContent.visibility = View.VISIBLE
                    mLlRequestFromTouristAllRequestsContent.visibility = View.GONE
                    mRlSearchRoot.visibility = View.INVISIBLE
                }else{
                    mSvRequestFromTouristCreateContent.visibility = View.GONE
                    mLlRequestFromTouristAllRequestsContent.visibility = View.VISIBLE
                    mRlSearchRoot.visibility = View.VISIBLE
                    relocateDataInTouristPackageRequestFromTourists()
                }
            }
            2->{
                mLlPackageOffersContentRoot.visibility = View.GONE
                mLlRequestFromTouristContentRoot.visibility = View.GONE
                mSvOfferPackageContentRoot.visibility = View.VISIBLE
                mRlSearchRoot.visibility = View.INVISIBLE
            }
        }
    }

    fun relocateDataInPackageOffers() {
        if (mIsLoadedPackageOffers) {
            mTvPagesPackageOffers.text = String.format(getString(R.string.pagination_items_text), mArrPackageOffers.size, mTotalItemsInPackageOffers)
            GlobalConstant.relocatePageNumbersInPaginator(
                mLlPaginationPagesPackageOffers.findViewById(R.id.ll_pf_page_numbers1),
                mCurPageNumInPackageOffers,
                mTotalPagesPackageOffers,
                requireActivity(),
                mListenerPackageOffers
            )
            mAdapterRvTouristPackageOffers.setData(
                requireActivity(),
                mArrPackageOffers
            )
            mRvTouristPackageOffers.adapter = mAdapterRvTouristPackageOffers
            mAdapterRvTouristPackageOffers.notifyDataSetChanged()
            mIsLoadedPackageOffers = false
            if (mTotalPagesPackageOffers < 2){
                mLlPaginationPackageOffersRoot.visibility = View.GONE
            }else{
                mLlPaginationPackageOffersRoot.visibility = View.VISIBLE
            }
        }else{
            reloadDataForPackageOffers()
        }
    }

    fun relocateDataInTouristPackageRequestFromTourists() {
        if (mIsLoadedTouristPackageRequestFromTourists) {
            mTvPagesTouristPackageRequestFromTourists.text = String.format(getString(R.string.pagination_items_text), mArrTouristPackageRequestFromTourists.size, mTotalItemsInTouristPackageRequestFromTourists)
            GlobalConstant.relocatePageNumbersInPaginator(
                mLlPaginationPagesRequestFromTourists.findViewById(R.id.ll_pf_page_numbers2),
                mCurPageNumInTouristPackageRequestFromTourists,
                mTotalPagesTouristPackageRequestFromTourists,
                requireActivity(),
                mListenerTouristPackageRequestFromTourists
            )
            mAdapterRvTouristPackageRequestFromTourists.setData(
                requireActivity(),
                mArrTouristPackageRequestFromTourists
            )
            mRvTouristPackageRequestFromTourists.adapter = mAdapterRvTouristPackageRequestFromTourists
            mAdapterRvTouristPackageRequestFromTourists.notifyDataSetChanged()
            mIsLoadedTouristPackageRequestFromTourists = false

            if (mTotalPagesTouristPackageRequestFromTourists < 2){
                mLlPaginationRequestFromTouristsRoot.visibility = View.GONE
            }else{
                mLlPaginationRequestFromTouristsRoot.visibility = View.VISIBLE
            }
        }else{
            reloadDataForTouristPackageRequestFromTourists()
        }
    }
}