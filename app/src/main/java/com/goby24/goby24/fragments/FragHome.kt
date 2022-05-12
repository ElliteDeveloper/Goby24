package com.goby24.goby24.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goby24.goby24.MainActivity
import com.goby24.goby24.R
import com.goby24.goby24.ui.SliderViewAdapterHomepage
import com.smarteist.autoimageslider.SliderView

class FragHome: Fragment() {

    private lateinit var mRootView:View
    private lateinit var mSliderImage:SliderView


    companion object {
        fun newInstance(): FragHome {
            return FragHome()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden){
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).showOrHideFooter(true)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.frag_home, container, false)
        initView()
        if (requireActivity() is MainActivity){
            (requireActivity() as MainActivity).showOrHideFooter(true)
        }
        return mRootView;
    }

    private fun initView(){
        mSliderImage = mRootView.findViewById(R.id.slider_home)
        val mSliderAdapter = SliderViewAdapterHomepage()
        mSliderAdapter.setData(requireActivity())
        mSliderImage.setSliderAdapter(mSliderAdapter)
        mSliderImage.startAutoCycle()

        mRootView.findViewById<View>(R.id.ll_frag_home_find_ride_root).setOnClickListener({

        })

        mRootView.findViewById<View>(R.id.ll_frag_home_offer_ride_root).setOnClickListener({

        })

        mRootView.findViewById<View>(R.id.ll_frag_home_tourist_package_root).setOnClickListener({

        })
    }
}