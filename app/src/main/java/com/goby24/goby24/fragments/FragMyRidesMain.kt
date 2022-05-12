package com.goby24.goby24.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.goby24.goby24.GlobalConstant
import com.goby24.goby24.MainActivity
import com.goby24.goby24.R
import com.goby24.goby24.globals.ApiInterface
import com.goby24.goby24.globals.RetrofitInstance
import com.goby24.goby24.models.*
import com.goby24.goby24.ui.*
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Suppress("DEPRECATION")
class FragMyRidesMain: Fragment() {

    private lateinit var mRootView:View
    private lateinit var mLlPaginationPrev:View
    private lateinit var mLlPaginationNext:View
    private lateinit var mLlPaginationPages: LinearLayout
    private lateinit var mTvPages:TextView
    private lateinit var mArrBasicRideAsTraveller:ArrayList<RideDataForBasicRideAsTravellerModel>
    private lateinit var mArrBasicRideAsTravellerPageData:ArrayList<RideDataForBasicRideAsTravellerModel>

    private lateinit var mArrBasicRideAsRider:ArrayList<RideDataForBasicRideAsRiderModel>
    private lateinit var mArrBasicRideAsRiderPageData:ArrayList<RideDataForBasicRideAsRiderModel>

    private lateinit var mArrRideRequestAsTraveller:ArrayList<RideDataForRideRequestAsTravellerModel>
    private lateinit var mArrRideRequestAsTravellerPageData:ArrayList<RideDataForRideRequestAsTravellerModel>

    private lateinit var mArrRideRequestAsRider:ArrayList<RideDataForRideRequestAsRiderModel>
    private lateinit var mArrRideRequestAsRiderPageData:ArrayList<RideDataForRideRequestAsRiderModel>

    private lateinit var mArrTouristPackageAsTraveller:ArrayList<RideDataForTouristPackageAsTravellerModel>
    private lateinit var mArrTouristPackageAsTravellerPageData:ArrayList<RideDataForTouristPackageAsTravellerModel>

    private lateinit var mArrTouristPackageAsRider:ArrayList<RideDataForTouristPackageAsRiderModel>
    private lateinit var mArrTouristPackageAsRiderPageData:ArrayList<RideDataForTouristPackageAsRiderModel>

    private lateinit var mArrTouristPackageRequestAsTraveller:ArrayList<RideDataForTouristPackageRequestAsTravellerModel>
    private lateinit var mArrTouristPackageRequestAsTravellerPageData:ArrayList<RideDataForTouristPackageRequestAsTravellerModel>

    private lateinit var mArrTouristPackageRequestAsRider:ArrayList<RideDataForTouristPackageRequestAsRiderModel>
    private lateinit var mArrTouristPackageRequestAsRiderPageData:ArrayList<RideDataForTouristPackageRequestAsRiderModel>

    private lateinit var mLvData:ListView
    private lateinit var mAdapterBasicAsTraveller:AdapterRideHistoryForBasicAsTravellerListViewItem
    private lateinit var mAdapterBasicAsRider: AdapterRideHistoryForBasicAsRiderListViewItem
    private lateinit var mAdapterRideRequestAsTraveller: AdapterRideHistoryForRideRequestAsTravellerListViewItem
    private lateinit var mAdapterRideRequestAsRider: AdapterRideHistoryForRideRequestAsRiderListViewItem
    private lateinit var mAdapterTouristPackageAsTraveller: AdapterRideHistoryForTouristPackageAsTravellerListViewItem
    private lateinit var mAdapterTouristPackageAsRider: AdapterRideHistoryForTouristPackageAsRiderListViewItem
    private lateinit var mAdapterTouristPackageRequestAsTraveller: AdapterRideHistoryForTouristPackageRequestAsTravellerListViewItem
    private lateinit var mAdapterTouristPackageRequestAsRider: AdapterRideHistoryForTouristPackageRequestAsRiderListViewItem

    private lateinit var mLlPaginationRoot:View
    private lateinit var mTlMainHeader:TabLayout
    private lateinit var mTlSubHeader:TabLayout

    private var mItemsPerPage = 5
    private var mCurPageNumInBasicRideAsTraveller = 1
    private var mCurPageNumInBasicRideAsRider = 1
    private var mCurPageNumInRideRequestAsTraveller = 1
    private var mCurPageNumInRideRequestAsRider = 1
    private var mCurPageNumInTouristPackageAsTraveller = 1
    private var mCurPageNumInTouristPackageAsRider = 1
    private var mCurPageNumInTouristPackageRequestAsTraveller = 1
    private var mCurPageNumInTouristPackageRequestAsRider = 1

    private var mTotalItemsInBasicRideAsTraveller = 0
    private var mTotalItemsInBasicRideAsRider = 0
    private var mTotalItemsInRideRequestAsTraveller = 0
    private var mTotalItemsInRideRequestAsRider = 0
    private var mTotalItemsInTouristPackageAsTraveller = 0
    private var mTotalItemsInTouristPackageAsRider = 0
    private var mTotalItemsInTouristPackageRequestAsTraveller = 0
    private var mTotalItemsInTouristPackageRequestAsRider = 0

    private var mTotalPagesBasicAsTraveller:Int = 0
    private var mTotalPagesBasicAsRider:Int = 0
    private var mTotalPagesRideRequestAsTraveller:Int = 0
    private var mTotalPagesRideRequestAsRider:Int = 0
    private var mTotalPagesTouristPackageAsTraveller:Int = 0
    private var mTotalPagesTouristPackageAsRider:Int = 0
    private var mTotalPagesTouristPackageRequestAsTraveller:Int = 0
    private var mTotalPagesTouristPackageRequestAsRider:Int = 0

    private var mListenerBasicAsTraveller = object: OnPaginatorPageNumberClickedListener(){
        override fun onPageNumberClicked(iPageNumber: Int) {
            if (mCurPageNumInBasicRideAsTraveller == iPageNumber) return
            mCurPageNumInBasicRideAsTraveller = iPageNumber
            relocateDataInBasicAsTraveller(mCurPageNumInBasicRideAsTraveller)
        }
    }

    private var mListenerBasicAsRider = object: OnPaginatorPageNumberClickedListener(){
        override fun onPageNumberClicked(iPageNumber: Int) {
            if (mCurPageNumInBasicRideAsRider == iPageNumber) return
            mCurPageNumInBasicRideAsRider = iPageNumber
            relocateDataInBasicAsRider(mCurPageNumInBasicRideAsRider)
        }
    }

    private var mListenerRideRequestAsTraveller = object: OnPaginatorPageNumberClickedListener(){
        override fun onPageNumberClicked(iPageNumber: Int) {
            if (mCurPageNumInRideRequestAsTraveller == iPageNumber) return
            mCurPageNumInRideRequestAsTraveller = iPageNumber
            relocateDataInRideRequestAsTraveller(mCurPageNumInRideRequestAsTraveller)
        }
    }

    private var mListenerRideRequestAsRider = object: OnPaginatorPageNumberClickedListener(){
        override fun onPageNumberClicked(iPageNumber: Int) {
            if (mCurPageNumInRideRequestAsRider == iPageNumber) return
            mCurPageNumInRideRequestAsRider = iPageNumber
            relocateDataInRideRequestAsRider(mCurPageNumInRideRequestAsRider)
        }
    }

    private var mListenerTouristPackageAsTraveller = object: OnPaginatorPageNumberClickedListener(){
        override fun onPageNumberClicked(iPageNumber: Int) {
            if (mCurPageNumInTouristPackageAsTraveller == iPageNumber) return
            mCurPageNumInTouristPackageAsTraveller = iPageNumber
            relocateDataInTouristPackageAsTraveller(mCurPageNumInTouristPackageAsTraveller)
        }
    }

    private var mListenerTouristPackageAsRider = object: OnPaginatorPageNumberClickedListener(){
        override fun onPageNumberClicked(iPageNumber: Int) {
            if (mCurPageNumInTouristPackageAsRider == iPageNumber) return
            mCurPageNumInTouristPackageAsRider = iPageNumber
            relocateDataInTouristPackageAsRider(mCurPageNumInTouristPackageAsRider)
        }
    }
    private var mListenerTouristPackageRequestAsTraveller = object: OnPaginatorPageNumberClickedListener(){
        override fun onPageNumberClicked(iPageNumber: Int) {
            if (mCurPageNumInTouristPackageRequestAsTraveller == iPageNumber) return
            mCurPageNumInTouristPackageRequestAsTraveller = iPageNumber
            relocateDataInTouristPackageRequestAsTraveller(mCurPageNumInTouristPackageRequestAsTraveller)
        }
    }

    private var mListenerTouristPackageRequestAsRider = object: OnPaginatorPageNumberClickedListener(){
        override fun onPageNumberClicked(iPageNumber: Int) {
            if (mCurPageNumInTouristPackageRequestAsRider == iPageNumber) return
            mCurPageNumInTouristPackageRequestAsRider = iPageNumber
            relocateDataInTouristPackageRequestAsRider(mCurPageNumInTouristPackageRequestAsRider)
        }
    }

    companion object {
        private var mMainTabSelectedIndex:Int = 0
        private var mSubTabSelectedIndex:Int = 0
        private var mIsLoadedBasicRideAsTraveller = false
        private var mIsLoadedBasicRideAsRider = false
        private var mIsLoadedRideRequestAsTraveller = false
        private var mIsLoadedRideRequestAsRider = false
        private var mIsLoadedTouristPackageAsTraveller = false
        private var mIsLoadedTouristPackageAsRider = false
        private var mIsLoadedTouristPackageRequestAsTraveller = false
        private var mIsLoadedTouristPackageRequestAsRider = false
        
        private var mProgressBasicRideAsTraveller = false
        private var mProgressBasicRideAsRider = false
        private var mProgressRideRequestAsTraveller = false
        private var mProgressRideRequestAsRider = false
        private var mProgressTouristPackageAsTraveller = false
        private var mProgressTouristPackageAsRider = false
        private var mProgressTouristPackageRequestAsTraveller = false
        private var mProgressTouristPackageRequestAsRider = false
        fun newInstance(): FragMyRidesMain {
            mMainTabSelectedIndex = 0
            mSubTabSelectedIndex = 0
            mIsLoadedBasicRideAsRider = false
            mIsLoadedBasicRideAsTraveller = false
            mIsLoadedRideRequestAsTraveller = false
            mIsLoadedRideRequestAsRider = false
            mIsLoadedTouristPackageAsTraveller = false
            mIsLoadedTouristPackageAsRider = false
            mIsLoadedTouristPackageRequestAsTraveller = false
            mIsLoadedTouristPackageRequestAsRider = false
            
            mProgressBasicRideAsRider = false
            mProgressBasicRideAsTraveller = false
            mProgressRideRequestAsTraveller = false
            mProgressRideRequestAsRider = false
            mProgressTouristPackageAsTraveller = false
            mProgressTouristPackageAsRider = false
            mProgressTouristPackageRequestAsTraveller = false
            mProgressTouristPackageRequestAsRider = false
            return FragMyRidesMain()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(R.layout.frag_myrides_main, container, false)
        initData()
        initView()
        return mRootView
    }

    private fun initData(){
        if (!mIsLoadedBasicRideAsTraveller) {
            mArrBasicRideAsTraveller = ArrayList()
            mArrBasicRideAsTravellerPageData = ArrayList()
        }
        if (!mIsLoadedBasicRideAsRider){
            mArrBasicRideAsRider = ArrayList()
            mArrBasicRideAsRiderPageData = ArrayList()
        }

        if (!mIsLoadedRideRequestAsTraveller) {
            mArrRideRequestAsTraveller = ArrayList()
            mArrRideRequestAsTravellerPageData = ArrayList()
        }
        if (!mIsLoadedRideRequestAsRider) {
            mArrRideRequestAsRider = ArrayList()
            mArrRideRequestAsRiderPageData = ArrayList()
        }

        if (!mIsLoadedTouristPackageAsTraveller) {
            mArrTouristPackageAsTraveller = ArrayList()
            mArrTouristPackageAsTravellerPageData = ArrayList()
        }
        if (!mIsLoadedTouristPackageAsRider) {
            mArrTouristPackageAsRider = ArrayList()
            mArrTouristPackageAsRiderPageData = ArrayList()
        }
        if (!mIsLoadedTouristPackageRequestAsTraveller) {
            mArrTouristPackageRequestAsTraveller = ArrayList()
            mArrTouristPackageRequestAsTravellerPageData = ArrayList()
        }
        if (!mIsLoadedTouristPackageRequestAsRider) {
            mArrTouristPackageRequestAsRider = ArrayList()
            mArrTouristPackageRequestAsRiderPageData = ArrayList()
        }
    }

    private fun reloadDataForBasicRideAsTraveller(){
        val progressDialog = ProgressDialog(requireActivity())
        if (!mProgressBasicRideAsTraveller) {
            progressDialog.show()
        }

        mProgressBasicRideAsTraveller = true
        mArrBasicRideAsTraveller = ArrayList()
        mArrBasicRideAsTravellerPageData = ArrayList()

        val retIn = RetrofitInstance.getRetrofitInstanceWithAccessTokenAndWithoutBody().create(ApiInterface::class.java)

        retIn.getBasicRideHistoryAsTraveller(mCurPageNumInBasicRideAsTraveller, mItemsPerPage).enqueue(object : Callback<ResponseBody> {
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
                                    val userListType: Type = object : TypeToken<ArrayList<RideDataForBasicRideAsTravellerModel?>?>() {}.type

                                    mArrBasicRideAsTraveller = gson.fromJson(jsonArray.toString(), userListType)
                                    mIsLoadedBasicRideAsTraveller = true
                                    mTotalPagesBasicAsTraveller = 1
                                    mTotalItemsInBasicRideAsTraveller = mArrBasicRideAsTraveller.size
                                    relocateDataInBasicAsTraveller(mCurPageNumInBasicRideAsTraveller)
                                }else{
                                    jsonObject = jsonObject.getJSONObject("result");
                                    val jsonArray = jsonObject.get("results") as JSONArray
                                    val gson = Gson()
                                    val userListType: Type = object : TypeToken<ArrayList<RideDataForBasicRideAsTravellerModel?>?>() {}.type
                                    mTotalItemsInBasicRideAsTraveller = jsonObject.optInt("count", 0)
                                    mArrBasicRideAsTraveller = gson.fromJson(jsonArray.toString(), userListType)
                                    if (mArrBasicRideAsTraveller.size == 0){
                                        mTotalPagesBasicAsTraveller = 0
                                    }else{
                                        mTotalPagesBasicAsTraveller = (mTotalItemsInBasicRideAsTraveller + mItemsPerPage - 1) / mItemsPerPage
                                    }
                                    mIsLoadedBasicRideAsTraveller = true
                                    relocateDataInBasicAsTraveller(mCurPageNumInBasicRideAsTraveller)
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

    private fun reloadDataForBasicRideAsRider(){
        val progressDialog = ProgressDialog(requireActivity())
        if (!mProgressBasicRideAsRider) {
            progressDialog.show()
        }
        mProgressBasicRideAsRider = true
        mArrBasicRideAsRider = ArrayList()
        mArrBasicRideAsRiderPageData = ArrayList()

        val retIn = RetrofitInstance.getRetrofitInstanceWithAccessTokenAndWithoutBody().create(
            ApiInterface::class.java)

        retIn.getBasicRideHistoryAsRider(mCurPageNumInBasicRideAsRider, mItemsPerPage).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                if (progressDialog.isShowing) progressDialog.hide()
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
                                    val userListType: Type = object : TypeToken<ArrayList<RideDataForBasicRideAsRiderModel?>?>() {}.type

                                    mArrBasicRideAsRider = gson.fromJson(jsonArray.toString(), userListType)
                                    mIsLoadedBasicRideAsRider = true
                                    mTotalPagesBasicAsRider = 1
                                    mTotalItemsInBasicRideAsRider = mArrBasicRideAsRider.size
                                    relocateDataInBasicAsRider(mCurPageNumInBasicRideAsRider)
                                }else{
                                    jsonObject = jsonObject.getJSONObject("result");
                                    val jsonArray = jsonObject.get("results") as JSONArray
                                    val gson = Gson()
                                    val userListType: Type = object : TypeToken<ArrayList<RideDataForBasicRideAsRiderModel?>?>() {}.type

                                    mArrBasicRideAsRider = gson.fromJson(jsonArray.toString(), userListType)
                                    mTotalItemsInBasicRideAsRider = jsonObject.optInt("count", 0)
                                    if (mArrBasicRideAsRider.size == 0){
                                        mTotalPagesBasicAsRider = 0
                                    }else{
                                        mTotalPagesBasicAsRider = (mTotalItemsInBasicRideAsRider + mItemsPerPage - 1) / mItemsPerPage
                                    }
                                    mIsLoadedBasicRideAsRider = true
                                    relocateDataInBasicAsRider(mCurPageNumInBasicRideAsRider)
                                }
                            }
                        }else{
                            Toast.makeText(requireActivity(), R.string.global_failure, Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(requireActivity(), R.string.global_failure, Toast.LENGTH_SHORT).show()
                    }
                    if (progressDialog.isShowing) progressDialog.hide()
                }catch(e:Exception){
                    if (progressDialog.isShowing) progressDialog.hide()
                    Toast.makeText(requireActivity(), R.string.global_failure, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun reloadDataForRideRequestAsTraveller(){
        val progressDialog = ProgressDialog(requireActivity())
        if (!mProgressRideRequestAsTraveller) {
            progressDialog.show()
        }
        mProgressRideRequestAsTraveller = true
        mArrRideRequestAsTraveller = ArrayList()
        mArrRideRequestAsTravellerPageData = ArrayList()

        val retIn = RetrofitInstance.getRetrofitInstanceWithAccessTokenAndWithoutBody().create(
            ApiInterface::class.java)

        retIn.getRideRequestHistoryAsTraveller(mCurPageNumInRideRequestAsTraveller, mItemsPerPage).enqueue(object : Callback<ResponseBody> {
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
                                    val userListType: Type = object : TypeToken<ArrayList<RideDataForRideRequestAsTravellerModel?>?>() {}.type

                                    mArrRideRequestAsTraveller = gson.fromJson(jsonArray.toString(), userListType)
                                    mIsLoadedRideRequestAsTraveller = true
                                    mTotalPagesRideRequestAsTraveller = 1
                                    mTotalItemsInRideRequestAsTraveller = mArrRideRequestAsTraveller.size
                                    relocateDataInRideRequestAsTraveller(mCurPageNumInRideRequestAsTraveller)
                                }else{
                                    jsonObject = jsonObject.getJSONObject("result");
                                    val jsonArray = jsonObject.get("results") as JSONArray
                                    val gson = Gson()
                                    val userListType: Type = object : TypeToken<ArrayList<RideDataForRideRequestAsTravellerModel?>?>() {}.type

                                    mArrRideRequestAsTraveller = gson.fromJson(jsonArray.toString(), userListType)
                                    mTotalItemsInRideRequestAsTraveller = jsonObject.optInt("count", 0)
                                    if (mArrRideRequestAsTraveller.size == 0){
                                        mTotalPagesRideRequestAsTraveller = 0
                                    }else{
                                        mTotalPagesRideRequestAsTraveller = (mTotalItemsInBasicRideAsTraveller + mItemsPerPage - 1) / mItemsPerPage
                                    }
                                    mIsLoadedRideRequestAsTraveller = true
                                    relocateDataInRideRequestAsTraveller(mCurPageNumInRideRequestAsTraveller)
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

    private fun reloadDataForRideRequestAsRider(){
        val progressDialog = ProgressDialog(requireActivity())
        if (!mProgressRideRequestAsRider) {
            progressDialog.show()
        }
        mProgressRideRequestAsRider = true
        mArrRideRequestAsRider = ArrayList()
        mArrRideRequestAsRiderPageData = ArrayList()

        val retIn = RetrofitInstance.getRetrofitInstanceWithAccessTokenAndWithoutBody().create(
            ApiInterface::class.java)

        retIn.getRideRequestHistoryAsRider(mCurPageNumInRideRequestAsRider, mItemsPerPage).enqueue(object : Callback<ResponseBody> {
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
                                    val userListType: Type = object : TypeToken<ArrayList<RideDataForRideRequestAsRiderModel?>?>() {}.type

                                    mArrRideRequestAsRider = gson.fromJson(jsonArray.toString(), userListType)
                                    mIsLoadedRideRequestAsRider = true
                                    mTotalPagesRideRequestAsRider = 1
                                    mTotalItemsInRideRequestAsRider = mArrRideRequestAsRider.size
                                    relocateDataInRideRequestAsRider(mCurPageNumInRideRequestAsRider)
                                }else{
                                    jsonObject = jsonObject.getJSONObject("result");
                                    val jsonArray = jsonObject.get("results") as JSONArray
                                    val gson = Gson()
                                    val userListType: Type = object : TypeToken<ArrayList<RideDataForRideRequestAsRiderModel?>?>() {}.type

                                    mArrRideRequestAsRider = gson.fromJson(jsonArray.toString(), userListType)
                                    mTotalItemsInRideRequestAsRider = jsonObject.optInt("count", 0)
                                    if (mArrRideRequestAsRider.size == 0){
                                        mTotalPagesRideRequestAsRider = 0
                                    }else{
                                        mTotalPagesRideRequestAsRider = (mTotalItemsInRideRequestAsRider + mItemsPerPage - 1) / mItemsPerPage
                                    }
                                    mIsLoadedRideRequestAsRider = true
                                    relocateDataInRideRequestAsRider(mCurPageNumInRideRequestAsRider)
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

    private fun reloadDataForTouristPackageAsTraveller(){
        val progressDialog = ProgressDialog(requireActivity())
        if (!mProgressTouristPackageAsTraveller) {
            progressDialog.show()
        }
        mProgressTouristPackageAsTraveller = true
        mArrTouristPackageAsTraveller = ArrayList()
        mArrTouristPackageAsTravellerPageData = ArrayList()

        val retIn = RetrofitInstance.getRetrofitInstanceWithAccessTokenAndWithoutBody().create(
            ApiInterface::class.java)

        retIn.getTouristPackageHistoryAsTraveller(mCurPageNumInTouristPackageAsTraveller, mItemsPerPage).enqueue(object : Callback<ResponseBody> {
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
                                    val userListType: Type = object : TypeToken<ArrayList<RideDataForTouristPackageAsTravellerModel?>?>() {}.type

                                    mArrTouristPackageAsTraveller = gson.fromJson(jsonArray.toString(), userListType)
                                    mIsLoadedTouristPackageAsTraveller = true
                                    mTotalPagesTouristPackageAsTraveller = 1
                                    mTotalItemsInTouristPackageAsTraveller = mArrTouristPackageAsTraveller.size
                                    relocateDataInTouristPackageAsTraveller(mCurPageNumInTouristPackageAsTraveller)
                                }else{
                                    jsonObject = jsonObject.getJSONObject("result");
                                    val jsonArray = jsonObject.get("results") as JSONArray
                                    val gson = Gson()
                                    val userListType: Type = object : TypeToken<ArrayList<RideDataForTouristPackageAsTravellerModel?>?>() {}.type

                                    mArrTouristPackageAsTraveller = gson.fromJson(jsonArray.toString(), userListType)
                                    mTotalItemsInTouristPackageAsTraveller = jsonObject.optInt("count", 0)
                                    if (mArrTouristPackageAsTraveller.size == 0){
                                        mTotalPagesTouristPackageAsTraveller = 0
                                    }else{
                                        mTotalPagesTouristPackageAsTraveller = (mTotalItemsInTouristPackageAsTraveller + mItemsPerPage - 1) / mItemsPerPage
                                    }
                                    mIsLoadedTouristPackageAsTraveller = true
                                    relocateDataInTouristPackageAsTraveller(mCurPageNumInTouristPackageAsTraveller)
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

    private fun reloadDataForTouristPackageAsRider(){
        val progressDialog = ProgressDialog(requireActivity())
        if (!mProgressTouristPackageAsRider) {
            progressDialog.show()
        }
        mProgressTouristPackageAsRider = true
        mArrTouristPackageAsRider = ArrayList()
        mArrTouristPackageAsRiderPageData = ArrayList()

        val retIn = RetrofitInstance.getRetrofitInstanceWithAccessTokenAndWithoutBody().create(
            ApiInterface::class.java)

        retIn.getTouristPackageHistoryAsRider(mCurPageNumInTouristPackageAsRider, mItemsPerPage).enqueue(object : Callback<ResponseBody> {
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
                                    val userListType: Type = object : TypeToken<ArrayList<RideDataForTouristPackageAsRiderModel?>?>() {}.type

                                    mArrTouristPackageAsRider = gson.fromJson(jsonArray.toString(), userListType)
                                    mIsLoadedTouristPackageAsRider = true
                                    mTotalPagesTouristPackageAsRider = 1
                                    mTotalItemsInTouristPackageAsRider = mArrTouristPackageAsRider.size
                                    relocateDataInTouristPackageAsRider(mCurPageNumInTouristPackageAsRider)
                                }else{
                                    jsonObject = jsonObject.getJSONObject("result");
                                    val jsonArray = jsonObject.get("results") as JSONArray
                                    val gson = Gson()
                                    val userListType: Type = object : TypeToken<ArrayList<RideDataForTouristPackageAsRiderModel?>?>() {}.type

                                    mArrTouristPackageAsRider = gson.fromJson(jsonArray.toString(), userListType)
                                    mTotalItemsInTouristPackageAsRider = jsonObject.optInt("count", 0)
                                    if (mArrTouristPackageAsRider.size == 0) {
                                        mTotalPagesTouristPackageAsRider = 0
                                    }else{
                                        mTotalPagesTouristPackageAsRider = (mTotalItemsInTouristPackageAsRider + mItemsPerPage - 1) / mItemsPerPage
                                    }
                                    mIsLoadedTouristPackageAsRider = true
                                    relocateDataInTouristPackageAsRider(mCurPageNumInTouristPackageAsRider)
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
    private fun reloadDataForTouristPackageRequestAsTraveller(){
        val progressDialog = ProgressDialog(requireActivity())
        if (!mProgressTouristPackageRequestAsTraveller) {
            progressDialog.show()
        }
        mProgressTouristPackageRequestAsTraveller = true
        mArrTouristPackageRequestAsTraveller = ArrayList()
        mArrTouristPackageRequestAsTravellerPageData = ArrayList()

        val retIn = RetrofitInstance.getRetrofitInstanceWithAccessTokenAndWithoutBody().create(
            ApiInterface::class.java)

        retIn.getTouristPackageRequestHistoryAsTraveller(mCurPageNumInTouristPackageRequestAsTraveller, mItemsPerPage).enqueue(object : Callback<ResponseBody> {
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
                                    val userListType: Type = object : TypeToken<ArrayList<RideDataForTouristPackageRequestAsTravellerModel?>?>() {}.type

                                    mArrTouristPackageRequestAsTraveller = gson.fromJson(jsonArray.toString(), userListType)
                                    mIsLoadedTouristPackageRequestAsTraveller = true
                                    mTotalPagesTouristPackageRequestAsTraveller = 1
                                    mTotalItemsInTouristPackageRequestAsTraveller = mArrTouristPackageRequestAsTraveller.size
                                    relocateDataInTouristPackageRequestAsTraveller(mCurPageNumInTouristPackageRequestAsTraveller)
                                }else{
                                    jsonObject = jsonObject.getJSONObject("result");
                                    val jsonArray = jsonObject.get("results") as JSONArray
                                    val gson = Gson()
                                    val userListType: Type = object : TypeToken<ArrayList<RideDataForTouristPackageRequestAsTravellerModel?>?>() {}.type

                                    mArrTouristPackageRequestAsTraveller = gson.fromJson(jsonArray.toString(), userListType)
                                    mTotalItemsInTouristPackageRequestAsTraveller = jsonObject.optInt("count", 0)
                                    if (mArrTouristPackageRequestAsTraveller.size == 0){
                                        mTotalPagesTouristPackageRequestAsTraveller = 0
                                    }else{
                                        mTotalPagesTouristPackageRequestAsTraveller = (mTotalItemsInTouristPackageRequestAsTraveller + mItemsPerPage - 1) / mItemsPerPage
                                    }
                                    mIsLoadedTouristPackageRequestAsTraveller = true
                                    relocateDataInTouristPackageRequestAsTraveller(mCurPageNumInTouristPackageRequestAsTraveller)
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

    private fun reloadDataForTouristPackageRequestAsRider(){
        val progressDialog = ProgressDialog(requireActivity())
        if (!mProgressTouristPackageRequestAsRider) {
            progressDialog.show()
        }
        mProgressTouristPackageRequestAsRider = true
        mArrTouristPackageRequestAsRider = ArrayList()
        mArrTouristPackageRequestAsRiderPageData = ArrayList()

        val retIn = RetrofitInstance.getRetrofitInstanceWithAccessTokenAndWithoutBody().create(
            ApiInterface::class.java)

        retIn.getTouristPackageRequestHistoryAsRider(mCurPageNumInTouristPackageRequestAsRider, mItemsPerPage).enqueue(object : Callback<ResponseBody> {
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
                                    val userListType: Type = object : TypeToken<ArrayList<RideDataForTouristPackageRequestAsRiderModel?>?>() {}.type

                                    mArrTouristPackageRequestAsRider = gson.fromJson(jsonArray.toString(), userListType)
                                    mIsLoadedTouristPackageRequestAsRider = true
                                    mTotalPagesTouristPackageRequestAsRider = 1
                                    mTotalItemsInTouristPackageRequestAsRider = mArrTouristPackageRequestAsRider.size
                                    relocateDataInTouristPackageRequestAsRider(mCurPageNumInTouristPackageRequestAsRider)
                                }else{
                                    jsonObject = jsonObject.getJSONObject("result");
                                    val jsonArray = jsonObject.get("results") as JSONArray
                                    val gson = Gson()
                                    val userListType: Type = object : TypeToken<ArrayList<RideDataForTouristPackageRequestAsRiderModel?>?>() {}.type

                                    mArrTouristPackageRequestAsRider = gson.fromJson(jsonArray.toString(), userListType)
                                    mTotalItemsInTouristPackageRequestAsRider = jsonObject.optInt("count", 0)
                                    if (mArrTouristPackageRequestAsRider.size == 0) {
                                        mTotalPagesTouristPackageRequestAsRider = 0
                                    }else{
                                        mTotalPagesTouristPackageRequestAsRider = (mTotalItemsInTouristPackageRequestAsRider + mItemsPerPage - 1) / mItemsPerPage
                                    }
                                    mIsLoadedTouristPackageRequestAsRider = true
                                    relocateDataInTouristPackageRequestAsRider(mCurPageNumInTouristPackageRequestAsRider)
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
        mLlPaginationPrev = mRootView.findViewById(R.id.ll_pf_previous)
        mLlPaginationNext = mRootView.findViewById(R.id.ll_pf_next)
        mLlPaginationPages = mRootView.findViewById(R.id.ll_pf_page_numbers)
        mLlPaginationRoot = mRootView.findViewById(R.id.ll_frag_myrides_footer_navigation_root)
        mTlMainHeader = mRootView.findViewById(R.id.tl_frag_myrides_main_main_category)
        mTlSubHeader = mRootView.findViewById(R.id.tl_frag_myrides_main_sub_category)

        mAdapterBasicAsTraveller = AdapterRideHistoryForBasicAsTravellerListViewItem(requireActivity())
        mAdapterBasicAsRider = AdapterRideHistoryForBasicAsRiderListViewItem(requireActivity())
        mAdapterRideRequestAsTraveller = AdapterRideHistoryForRideRequestAsTravellerListViewItem(requireActivity())
        mAdapterRideRequestAsRider = AdapterRideHistoryForRideRequestAsRiderListViewItem(requireActivity())
        mAdapterTouristPackageAsTraveller = AdapterRideHistoryForTouristPackageAsTravellerListViewItem(requireActivity())
        mAdapterTouristPackageAsRider = AdapterRideHistoryForTouristPackageAsRiderListViewItem(requireActivity())
        mAdapterTouristPackageRequestAsTraveller = AdapterRideHistoryForTouristPackageRequestAsTravellerListViewItem(requireActivity())
        mAdapterTouristPackageRequestAsRider = AdapterRideHistoryForTouristPackageRequestAsRiderListViewItem(requireActivity())

        mLvData = mRootView.findViewById(R.id.lv_frag_myrides)

        when(mMainTabSelectedIndex){
            0->{
                if (mSubTabSelectedIndex == 0){
                    GlobalConstant.relocatePageNumbersInPaginator(
                        mLlPaginationPages.findViewById(R.id.ll_pf_page_numbers),
                        mCurPageNumInBasicRideAsTraveller,
                        mArrBasicRideAsTraveller.size,
                        mItemsPerPage,
                        requireActivity(),
                        mListenerBasicAsTraveller
                    )
                    relocateDataInBasicAsTraveller(mCurPageNumInBasicRideAsTraveller)
                    if (mArrBasicRideAsTraveller.size < mItemsPerPage){
                        mLlPaginationRoot.visibility = View.GONE
                    }else{
                        mLlPaginationRoot.visibility = View.VISIBLE
                    }
                }else{
                    GlobalConstant.relocatePageNumbersInPaginator(
                        mLlPaginationPages.findViewById(R.id.ll_pf_page_numbers),
                        mCurPageNumInBasicRideAsRider,
                        mArrBasicRideAsRider.size,
                        mItemsPerPage,
                        requireActivity(),
                        mListenerBasicAsRider
                    )
                    relocateDataInBasicAsRider(mCurPageNumInBasicRideAsRider)
                    if (mArrBasicRideAsRider.size < mItemsPerPage){
                        mLlPaginationRoot.visibility = View.GONE
                    }else{
                        mLlPaginationRoot.visibility = View.VISIBLE
                    }
                }
            }
            1->{
                if (mSubTabSelectedIndex == 0){
                    GlobalConstant.relocatePageNumbersInPaginator(
                        mLlPaginationPages.findViewById(R.id.ll_pf_page_numbers),
                        mCurPageNumInRideRequestAsTraveller,
                        mArrRideRequestAsTraveller.size,
                        mItemsPerPage,
                        requireActivity(),
                        mListenerRideRequestAsTraveller
                    )
                    relocateDataInRideRequestAsTraveller(mCurPageNumInRideRequestAsTraveller)
                    if (mArrRideRequestAsTraveller.size < mItemsPerPage){
                        mLlPaginationRoot.visibility = View.GONE
                    }else{
                        mLlPaginationRoot.visibility = View.VISIBLE
                    }
                }else{
                    GlobalConstant.relocatePageNumbersInPaginator(
                        mLlPaginationPages.findViewById(R.id.ll_pf_page_numbers),
                        mCurPageNumInRideRequestAsRider,
                        mArrRideRequestAsRider.size,
                        mItemsPerPage,
                        requireActivity(),
                        mListenerRideRequestAsRider
                    )
                    relocateDataInRideRequestAsRider(mCurPageNumInRideRequestAsRider)
                    if (mArrRideRequestAsRider.size < mItemsPerPage){
                        mLlPaginationRoot.visibility = View.GONE
                    }else{
                        mLlPaginationRoot.visibility = View.VISIBLE
                    }
                }
            }
            2->{
                if (mSubTabSelectedIndex == 0){
                    GlobalConstant.relocatePageNumbersInPaginator(
                        mLlPaginationPages.findViewById(R.id.ll_pf_page_numbers),
                        mCurPageNumInTouristPackageAsTraveller,
                        mArrTouristPackageAsTraveller.size,
                        mItemsPerPage,
                        requireActivity(),
                        mListenerTouristPackageAsTraveller
                    )
                    relocateDataInTouristPackageAsTraveller(mCurPageNumInTouristPackageAsTraveller)
                    if (mArrTouristPackageAsTraveller.size < mItemsPerPage){
                        mLlPaginationRoot.visibility = View.GONE
                    }else{
                        mLlPaginationRoot.visibility = View.VISIBLE
                    }
                }else{
                    GlobalConstant.relocatePageNumbersInPaginator(
                        mLlPaginationPages.findViewById(R.id.ll_pf_page_numbers),
                        mCurPageNumInTouristPackageAsRider,
                        mArrTouristPackageAsRider.size,
                        mItemsPerPage,
                        requireActivity(),
                        mListenerTouristPackageAsRider
                    )
                    relocateDataInTouristPackageAsRider(mCurPageNumInTouristPackageAsRider)
                    if (mArrTouristPackageAsRider.size < mItemsPerPage){
                        mLlPaginationRoot.visibility = View.GONE
                    }else{
                        mLlPaginationRoot.visibility = View.VISIBLE
                    }
                }
            }
            3->{
                if (mSubTabSelectedIndex == 0){
                    GlobalConstant.relocatePageNumbersInPaginator(
                        mLlPaginationPages.findViewById(R.id.ll_pf_page_numbers),
                        mCurPageNumInTouristPackageRequestAsTraveller,
                        mArrTouristPackageRequestAsTraveller.size,
                        mItemsPerPage,
                        requireActivity(),
                        mListenerTouristPackageRequestAsTraveller
                    )
                    relocateDataInTouristPackageRequestAsTraveller(mCurPageNumInTouristPackageRequestAsTraveller)
                    if (mArrTouristPackageRequestAsTraveller.size < mItemsPerPage){
                        mLlPaginationRoot.visibility = View.GONE
                    }else{
                        mLlPaginationRoot.visibility = View.VISIBLE
                    }
                }else{
                    GlobalConstant.relocatePageNumbersInPaginator(
                        mLlPaginationPages.findViewById(R.id.ll_pf_page_numbers),
                        mCurPageNumInTouristPackageRequestAsRider,
                        mArrTouristPackageRequestAsRider.size,
                        mItemsPerPage,
                        requireActivity(),
                        mListenerTouristPackageRequestAsRider
                    )
                    relocateDataInTouristPackageRequestAsRider(mCurPageNumInTouristPackageRequestAsRider)
                    if (mArrTouristPackageRequestAsRider.size < mItemsPerPage){
                        mLlPaginationRoot.visibility = View.GONE
                    }else{
                        mLlPaginationRoot.visibility = View.VISIBLE
                    }
                }
            }
        }

        mRootView.findViewById<View>(R.id.ll_pf_previous).setOnClickListener {
            when(mMainTabSelectedIndex) {
                0 -> {
                    if (mSubTabSelectedIndex == 0) {
                        if (mCurPageNumInBasicRideAsTraveller == 1) return@setOnClickListener
                        mCurPageNumInBasicRideAsTraveller--
                        relocateDataInBasicAsTraveller(mCurPageNumInBasicRideAsTraveller)
                    } else {
                        if (mCurPageNumInBasicRideAsRider == 1) return@setOnClickListener
                        mCurPageNumInBasicRideAsRider--
                        relocateDataInBasicAsRider(mCurPageNumInBasicRideAsRider)
                    }
                }
                1->{
                    if (mSubTabSelectedIndex == 0) {
                        if (mCurPageNumInRideRequestAsTraveller == 1) return@setOnClickListener
                        mCurPageNumInRideRequestAsTraveller--
                        relocateDataInRideRequestAsTraveller(mCurPageNumInRideRequestAsTraveller)
                    } else {
                        if (mCurPageNumInRideRequestAsRider == 1) return@setOnClickListener
                        mCurPageNumInRideRequestAsRider--
                        relocateDataInRideRequestAsRider(mCurPageNumInRideRequestAsRider)
                    }
                }
                2->{
                    if (mSubTabSelectedIndex == 0) {
                        if (mCurPageNumInTouristPackageAsTraveller == 1) return@setOnClickListener
                        mCurPageNumInTouristPackageAsTraveller--
                        relocateDataInTouristPackageAsTraveller(mCurPageNumInBasicRideAsTraveller)
                    } else {
                        if (mCurPageNumInTouristPackageAsRider == 1) return@setOnClickListener
                        mCurPageNumInTouristPackageAsRider--
                        relocateDataInTouristPackageAsRider(mCurPageNumInTouristPackageAsRider)
                    }
                }
                3->{
                    if (mSubTabSelectedIndex == 0) {
                        if (mCurPageNumInTouristPackageRequestAsTraveller == 1) return@setOnClickListener
                        mCurPageNumInTouristPackageRequestAsTraveller--
                        relocateDataInTouristPackageRequestAsTraveller(mCurPageNumInBasicRideAsTraveller)
                    } else {
                        if (mCurPageNumInTouristPackageRequestAsRider == 1) return@setOnClickListener
                        mCurPageNumInTouristPackageRequestAsRider--
                        relocateDataInTouristPackageRequestAsRider(mCurPageNumInTouristPackageRequestAsRider)
                    }
                }
            }                    
        }

        mRootView.findViewById<View>(R.id.ll_pf_next).setOnClickListener {
            when(mMainTabSelectedIndex) {
                0 -> {
                    if (mSubTabSelectedIndex == 0) {
                        if (mCurPageNumInBasicRideAsTraveller == mTotalPagesBasicAsTraveller) return@setOnClickListener
                        mCurPageNumInBasicRideAsTraveller++
                        relocateDataInBasicAsTraveller(mCurPageNumInBasicRideAsTraveller)
                    } else {
                        if (mCurPageNumInBasicRideAsRider == mTotalPagesBasicAsRider) return@setOnClickListener
                        mCurPageNumInBasicRideAsRider++
                        relocateDataInBasicAsRider(mCurPageNumInBasicRideAsRider)
                    }
                }
                1 -> {
                    if (mSubTabSelectedIndex == 0) {
                        if (mCurPageNumInRideRequestAsTraveller == mTotalPagesRideRequestAsTraveller) return@setOnClickListener
                        mCurPageNumInRideRequestAsTraveller++
                        relocateDataInRideRequestAsTraveller(mCurPageNumInRideRequestAsTraveller)
                    } else {
                        if (mCurPageNumInRideRequestAsRider == mTotalPagesRideRequestAsRider) return@setOnClickListener
                        mCurPageNumInRideRequestAsRider++
                        relocateDataInRideRequestAsRider(mCurPageNumInRideRequestAsRider)
                    }
                }
                2 -> {
                    if (mSubTabSelectedIndex == 0) {
                        if (mCurPageNumInTouristPackageAsTraveller == mTotalPagesTouristPackageAsTraveller) return@setOnClickListener
                        mCurPageNumInTouristPackageAsTraveller++
                        relocateDataInTouristPackageAsTraveller(mCurPageNumInTouristPackageAsTraveller)
                    } else {
                        if (mCurPageNumInTouristPackageAsRider == mTotalPagesTouristPackageAsRider) return@setOnClickListener
                        mCurPageNumInTouristPackageAsRider++
                        relocateDataInTouristPackageAsRider(mCurPageNumInTouristPackageAsRider)
                    }
                }
                3 -> {
                    if (mSubTabSelectedIndex == 0) {
                        if (mCurPageNumInTouristPackageRequestAsTraveller == mTotalPagesTouristPackageRequestAsTraveller) return@setOnClickListener
                        mCurPageNumInTouristPackageRequestAsTraveller++
                        relocateDataInTouristPackageRequestAsTraveller(mCurPageNumInTouristPackageRequestAsTraveller)
                    } else {
                        if (mCurPageNumInTouristPackageRequestAsRider == mTotalPagesTouristPackageRequestAsRider) return@setOnClickListener
                        mCurPageNumInTouristPackageRequestAsRider++
                        relocateDataInTouristPackageRequestAsRider(mCurPageNumInTouristPackageRequestAsRider)
                    }
                }
            }
        }

        mTvPages = mRootView.findViewById(R.id.tv_pf_msg)

        mRootView.findViewById<View>(R.id.iv_frag_myrides_main_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        mLvData.setOnItemClickListener { adapterView, view, position, l ->
            if (mLvData.adapter is AdapterRideHistoryForBasicAsTravellerListViewItem) {
                var itemData = mAdapterBasicAsTraveller.getItem(position) as RideDataForBasicRideAsTravellerModel
                if (requireActivity() is MainActivity) {
                    (requireActivity() as MainActivity).addFrag(
                        FragRideDetailForBasicAsTraveller.newInstance(
                            itemData,
                            mSubTabSelectedIndex == 1
                        ), true, true
                    )
                }
            }else if(mLvData.adapter is AdapterRideHistoryForBasicAsRiderListViewItem) {
                var itemData = mAdapterBasicAsRider.getItem(position) as RideDataForBasicRideAsRiderModel
                if (requireActivity() is MainActivity) {
                    (requireActivity() as MainActivity).addFrag(
                        FragRideDetailForBasicAsRider.newInstance(
                            itemData
                        ), true, true
                    )
                }
            }else if(mLvData.adapter is AdapterRideHistoryForRideRequestAsTravellerListViewItem){
                var itemData = mAdapterRideRequestAsTraveller.getItem(position) as RideDataForRideRequestAsTravellerModel
                if (requireActivity() is MainActivity) {
                    (requireActivity() as MainActivity).addFrag(
                        FragRideDetailForRideRequestAsTraveller.newInstance(
                            itemData
                        ), true, true
                    )
                }
            }else if(mLvData.adapter is AdapterRideHistoryForRideRequestAsRiderListViewItem){
                var itemData = mAdapterRideRequestAsRider.getItem(position) as RideDataForRideRequestAsRiderModel
                if (requireActivity() is MainActivity) {
                    (requireActivity() as MainActivity).addFrag(
                        FragRideDetailForRideRequestAsRider.newInstance(
                            itemData
                        ), true, true
                    )
                }
            }else if(mLvData.adapter is AdapterRideHistoryForTouristPackageAsTravellerListViewItem){
                var itemData = mAdapterTouristPackageAsTraveller.getItem(position) as RideDataForTouristPackageAsTravellerModel
                if (requireActivity() is MainActivity) {
                    (requireActivity() as MainActivity).addFrag(
                        FragRideDetailForTouristPackageAsTraveller.newInstance(
                            itemData
                        ), true, true
                    )
                }
            }else if(mLvData.adapter is AdapterRideHistoryForTouristPackageAsRiderListViewItem){
                var itemData = mAdapterTouristPackageAsRider.getItem(position) as RideDataForTouristPackageAsRiderModel
                if (requireActivity() is MainActivity) {
                    (requireActivity() as MainActivity).addFrag(
                        FragRideDetailForTouristPackageAsRider.newInstance(
                            itemData
                        ), true, true
                    )
                }
            }else if(mLvData.adapter is AdapterRideHistoryForTouristPackageRequestAsTravellerListViewItem){
                var itemData = mAdapterTouristPackageRequestAsTraveller.getItem(position) as RideDataForTouristPackageRequestAsTravellerModel
                if (requireActivity() is MainActivity) {
                    (requireActivity() as MainActivity).addFrag(
                        FragRideDetailForTouristPackageRequestAsTraveller.newInstance(
                            itemData
                        ), true, true
                    )
                }
            }else if(mLvData.adapter is AdapterRideHistoryForTouristPackageRequestAsRiderListViewItem){
                var itemData = mAdapterTouristPackageRequestAsRider.getItem(position) as RideDataForTouristPackageRequestAsRiderModel
                if (requireActivity() is MainActivity) {
                    (requireActivity() as MainActivity).addFrag(
                        FragRideDetailForTouristPackageRequestAsRider.newInstance(
                            itemData
                        ), true, true
                    )
                }
            }
        }

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
                when(mSubTabSelectedIndex){
                    0->{
                        relocateDataInBasicAsTraveller(mCurPageNumInBasicRideAsTraveller)
                    }
                    1->{
                        relocateDataInBasicAsRider(mCurPageNumInBasicRideAsRider)
                    }
                }
                mTlSubHeader.getTabAt(0)?.text = getString(R.string.rides_taken)
                mTlSubHeader.getTabAt(1)?.text = getString(R.string.rides_offered)
            }
            1->{
                when(mSubTabSelectedIndex) {
                    0 -> {
                        relocateDataInRideRequestAsTraveller(mCurPageNumInRideRequestAsTraveller)
                    }
                    1->{
                        relocateDataInRideRequestAsRider(mCurPageNumInRideRequestAsRider)                        
                    }
                }
                mTlSubHeader.getTabAt(0)?.text = getString(R.string.traveller)
                mTlSubHeader.getTabAt(1)?.text = getString(R.string.rider)
            }
            2->{
                when(mSubTabSelectedIndex) {
                    0 -> {
                        relocateDataInTouristPackageAsTraveller(mCurPageNumInTouristPackageAsTraveller)
                    }
                    1->{
                        relocateDataInTouristPackageAsRider(mCurPageNumInTouristPackageAsRider)                        
                    }
                }
                mTlSubHeader.getTabAt(0)?.text = getString(R.string.package_taken)
                mTlSubHeader.getTabAt(1)?.text = getString(R.string.package_offered)
            }
            3->{
                when(mSubTabSelectedIndex) {
                    0 -> {
                        relocateDataInTouristPackageRequestAsTraveller(mCurPageNumInTouristPackageRequestAsTraveller)
                    }
                    1->{
                        relocateDataInTouristPackageRequestAsRider(mCurPageNumInTouristPackageRequestAsRider)
                    }
                }
                mTlSubHeader.getTabAt(0)?.text = getString(R.string.traveller)
                mTlSubHeader.getTabAt(1)?.text = getString(R.string.rider)
            }
        }
    }

    fun relocateDataInBasicAsTraveller(iPageIndex:Int){
        if (mIsLoadedBasicRideAsTraveller) {
            mTvPages.text = String.format(getString(R.string.pagination_items_text), mArrBasicRideAsTraveller.size, mTotalItemsInBasicRideAsTraveller)
            GlobalConstant.relocatePageNumbersInPaginator(
                mLlPaginationPages.findViewById(R.id.ll_pf_page_numbers),
                mCurPageNumInBasicRideAsTraveller,
                mTotalPagesBasicAsTraveller,
                requireActivity(),
                mListenerBasicAsTraveller
            )
            mAdapterBasicAsTraveller.setData(
                requireActivity(),
                mArrBasicRideAsTraveller
            )
            mLvData.adapter = mAdapterBasicAsTraveller
            (mLvData.adapter as BaseAdapter).notifyDataSetChanged()
            mIsLoadedBasicRideAsTraveller = false
            if (mTotalPagesBasicAsTraveller < 2){
                mLlPaginationRoot.visibility = View.GONE
            }else{
                mLlPaginationRoot.visibility = View.VISIBLE
            }
        }else{
            reloadDataForBasicRideAsTraveller()
        }
    }

    fun relocateDataInBasicAsRider(iPageIndex:Int){
        if (mIsLoadedBasicRideAsRider) {
            mTvPages.text = String.format(getString(R.string.pagination_items_text), mArrBasicRideAsRider.size, mTotalItemsInBasicRideAsRider)
            GlobalConstant.relocatePageNumbersInPaginator(
                mLlPaginationPages.findViewById(R.id.ll_pf_page_numbers),
                mCurPageNumInBasicRideAsRider,
                mTotalPagesBasicAsRider,
                requireActivity(),
                mListenerBasicAsRider
            )
            mAdapterBasicAsRider.setData(requireActivity(), mArrBasicRideAsRider)
            mLvData.adapter = mAdapterBasicAsRider
            (mLvData.adapter as BaseAdapter).notifyDataSetChanged()
            mIsLoadedBasicRideAsRider = false
            if (mTotalPagesBasicAsRider < 2){
                mLlPaginationRoot.visibility = View.GONE
            }else{
                mLlPaginationRoot.visibility = View.VISIBLE
            }
        }else{
            reloadDataForBasicRideAsRider()
        }
    }

    fun relocateDataInRideRequestAsTraveller(iPageIndex:Int){
        if (mIsLoadedRideRequestAsTraveller) {
            mTvPages.text = String.format(getString(R.string.pagination_items_text), mArrRideRequestAsTraveller.size, mTotalItemsInRideRequestAsTraveller)
            GlobalConstant.relocatePageNumbersInPaginator(
                mLlPaginationPages.findViewById(R.id.ll_pf_page_numbers),
                mCurPageNumInRideRequestAsTraveller,
                mTotalPagesRideRequestAsTraveller,
                requireActivity(),
                mListenerRideRequestAsTraveller
            )
            mAdapterRideRequestAsTraveller.setData(
                requireActivity(),
                mArrRideRequestAsTraveller
            )
            mLvData.adapter = mAdapterRideRequestAsTraveller
            (mLvData.adapter as BaseAdapter).notifyDataSetChanged()
            mIsLoadedRideRequestAsTraveller = false
            if (mTotalPagesRideRequestAsTraveller < 2){
                mLlPaginationRoot.visibility = View.GONE
            }else{
                mLlPaginationRoot.visibility = View.VISIBLE
            }
        }else{
            reloadDataForRideRequestAsTraveller()
        }
    }

    fun relocateDataInRideRequestAsRider(iPageIndex:Int){
        if (mIsLoadedRideRequestAsRider) {
            mTvPages.text = String.format(getString(R.string.pagination_items_text), mArrRideRequestAsRider.size, mTotalItemsInRideRequestAsRider)
            GlobalConstant.relocatePageNumbersInPaginator(
                mLlPaginationPages.findViewById(R.id.ll_pf_page_numbers),
                mCurPageNumInRideRequestAsRider,
                mTotalPagesRideRequestAsRider,
                requireActivity(),
                mListenerRideRequestAsRider
            )
            mAdapterRideRequestAsRider.setData(
                requireActivity(),
                mArrRideRequestAsRider
            )
            mLvData.adapter = mAdapterRideRequestAsRider
            (mLvData.adapter as BaseAdapter).notifyDataSetChanged()
            mIsLoadedRideRequestAsRider = false
            if (mTotalPagesRideRequestAsRider < 2){
                mLlPaginationRoot.visibility = View.GONE
            }else{
                mLlPaginationRoot.visibility = View.VISIBLE
            }
        }else{
            reloadDataForRideRequestAsRider()
        }
    }
    
    fun relocateDataInTouristPackageAsTraveller(iPageIndex:Int){
        if (mIsLoadedTouristPackageAsTraveller) {
            mTvPages.text = String.format(getString(R.string.pagination_items_text), mArrTouristPackageAsTraveller.size, mTotalItemsInTouristPackageAsTraveller)
            GlobalConstant.relocatePageNumbersInPaginator(
                mLlPaginationPages.findViewById(R.id.ll_pf_page_numbers),
                mCurPageNumInTouristPackageAsTraveller,
                mTotalPagesTouristPackageAsTraveller,
                requireActivity(),
                mListenerTouristPackageAsTraveller
            )
            mAdapterTouristPackageAsTraveller.setData(
                requireActivity(),
                mArrTouristPackageAsTraveller
            )
            mLvData.adapter = mAdapterTouristPackageAsTraveller
            (mLvData.adapter as BaseAdapter).notifyDataSetChanged()
            mIsLoadedTouristPackageAsTraveller = false
            if (mTotalPagesTouristPackageAsTraveller < 2){
                mLlPaginationRoot.visibility = View.GONE
            }else{
                mLlPaginationRoot.visibility = View.VISIBLE
            }
        }else{
            reloadDataForTouristPackageAsTraveller()
        }
    }

    fun relocateDataInTouristPackageAsRider(iPageIndex:Int){
        if (mIsLoadedTouristPackageAsRider) {
            mTvPages.text = String.format(getString(R.string.pagination_items_text), mArrTouristPackageAsRider.size, mTotalItemsInTouristPackageAsRider)
            GlobalConstant.relocatePageNumbersInPaginator(
                mLlPaginationPages.findViewById(R.id.ll_pf_page_numbers),
                mCurPageNumInTouristPackageAsRider,
                mTotalPagesTouristPackageAsRider,
                requireActivity(),
                mListenerTouristPackageAsRider
            )
            mAdapterTouristPackageAsRider.setData(
                requireActivity(),
                mArrTouristPackageAsRider
            )
            mLvData.adapter = mAdapterTouristPackageAsRider
            (mLvData.adapter as BaseAdapter).notifyDataSetChanged()
            mIsLoadedTouristPackageAsRider = false
            if (mTotalPagesTouristPackageAsRider < 2){
                mLlPaginationRoot.visibility = View.GONE
            }else{
                mLlPaginationRoot.visibility = View.VISIBLE
            }
        }else{
            reloadDataForTouristPackageAsRider()
        }
    }

    fun relocateDataInTouristPackageRequestAsTraveller(iPageIndex:Int){
        if (mIsLoadedTouristPackageRequestAsTraveller) {
            mTvPages.text = String.format(getString(R.string.pagination_items_text), mArrTouristPackageRequestAsTraveller.size, mTotalItemsInTouristPackageRequestAsTraveller)
            GlobalConstant.relocatePageNumbersInPaginator(
                mLlPaginationPages.findViewById(R.id.ll_pf_page_numbers),
                mCurPageNumInTouristPackageRequestAsTraveller,
                mTotalPagesTouristPackageRequestAsTraveller,
                requireActivity(),
                mListenerTouristPackageRequestAsTraveller
            )
            mAdapterTouristPackageRequestAsTraveller.setData(
                requireActivity(),
                mArrTouristPackageRequestAsTraveller
            )
            mLvData.adapter = mAdapterTouristPackageRequestAsTraveller
            (mLvData.adapter as BaseAdapter).notifyDataSetChanged()
            mIsLoadedTouristPackageRequestAsTraveller = false
            if (mTotalPagesTouristPackageRequestAsTraveller < 2){
                mLlPaginationRoot.visibility = View.GONE
            }else{
                mLlPaginationRoot.visibility = View.VISIBLE
            }
        }else{
            reloadDataForTouristPackageRequestAsTraveller()
        }
    }

    fun relocateDataInTouristPackageRequestAsRider(iPageIndex:Int){
        if (mIsLoadedTouristPackageRequestAsRider) {
            mTvPages.text = String.format(getString(R.string.pagination_items_text), mArrTouristPackageRequestAsRider.size, mTotalItemsInTouristPackageRequestAsRider)
            GlobalConstant.relocatePageNumbersInPaginator(
                mLlPaginationPages.findViewById(R.id.ll_pf_page_numbers),
                mCurPageNumInTouristPackageRequestAsRider,
                mTotalPagesTouristPackageRequestAsRider,
                requireActivity(),
                mListenerTouristPackageRequestAsRider
            )
            mAdapterTouristPackageRequestAsRider.setData(
                requireActivity(),
                mArrTouristPackageRequestAsRider
            )
            mLvData.adapter = mAdapterTouristPackageRequestAsRider
            (mLvData.adapter as BaseAdapter).notifyDataSetChanged()
            mIsLoadedTouristPackageRequestAsRider = false
            if (mTotalPagesTouristPackageRequestAsRider < 2){
                mLlPaginationRoot.visibility = View.GONE
            }else{
                mLlPaginationRoot.visibility = View.VISIBLE
            }
        }else{
            reloadDataForTouristPackageRequestAsRider()
        }
    }
}