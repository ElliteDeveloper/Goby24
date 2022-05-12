package com.goby24.goby24.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.goby24.goby24.MainActivity
import com.goby24.goby24.R

class FragProfileEditTravelPreferences: Fragment() {

    private lateinit var mRootView:View
    private lateinit var mSpChattiness:Spinner
    private lateinit var mSpMusic:Spinner
    private lateinit var mSpSmoking:Spinner
    private lateinit var mSpPets:Spinner

    companion object {
        fun newInstance(): FragProfileEditTravelPreferences {
            return FragProfileEditTravelPreferences()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.frag_edit_travel_preferences, container, false)
        initView()
        return mRootView;
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden){
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).showOrHideFooter(false)
            }
        }
    }

    private fun initView(){
        mRootView.findViewById<View>(R.id.iv_frag_edit_travel_preferences_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        mSpChattiness = mRootView.findViewById(R.id.sp_frag_edit_travel_preferences_chattiness)
        mSpMusic = mRootView.findViewById(R.id.sp_frag_edit_travel_preferences_music)
        mSpSmoking = mRootView.findViewById(R.id.sp_frag_edit_travel_preferences_smoking)
        mSpPets = mRootView.findViewById(R.id.sp_frag_edit_travel_preferences_pets)
    }
}