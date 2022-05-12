package com.goby24.goby24.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.goby24.goby24.MainActivity
import com.goby24.goby24.R

class FragVehicleInfo: Fragment() {

    private lateinit var mRootView:View
    private lateinit var mEdBrand:EditText
    private lateinit var mEdModel:EditText
    private lateinit var mEdKind:EditText
    private lateinit var mEdDate:EditText

    companion object {
        fun newInstance(): FragVehicleInfo {
            return FragVehicleInfo()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(R.layout.frag_add_vehicle_info, container, false)
        initView()
        return mRootView
    }

    private fun initView(){
        mEdBrand = mRootView.findViewById(R.id.ed_frag_add_vehicle_info_brand)
        mEdModel = mRootView.findViewById(R.id.ed_frag_add_vehicle_info_model)
        mEdKind = mRootView.findViewById(R.id.ed_frag_add_vehicle_info_kind)
        mEdDate = mRootView.findViewById(R.id.ed_frag_add_vehicle_info_date)

        mRootView.findViewById<View>(R.id.iv_frag_add_vehicle_info_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        mRootView.findViewById<View>(R.id.tv_frag_add_vehicle_info_done).setOnClickListener {
            val strBrand:String = mEdBrand.text.toString()
            val strModel:String = mEdModel.text.toString()
            val strKind:String = mEdKind.text.toString()
            val strDate:String = mEdDate.text.toString()
            if (strBrand.isEmpty()){
                Toast.makeText(requireActivity(), R.string.enter_brand, Toast.LENGTH_LONG).show()
                mEdBrand.requestFocus()
                return@setOnClickListener
            }
            if (strModel.isEmpty()){
                Toast.makeText(requireActivity(), R.string.enter_model, Toast.LENGTH_LONG).show()
                mEdModel.requestFocus()
                return@setOnClickListener
            }
            if (strKind.isEmpty()){
                Toast.makeText(requireActivity(), R.string.enter_kind, Toast.LENGTH_LONG).show()
                mEdKind.requestFocus()
                return@setOnClickListener
            }
            if (strDate.isEmpty()){
                Toast.makeText(requireActivity(), R.string.enter_date, Toast.LENGTH_LONG).show()
                mEdDate.requestFocus()
                return@setOnClickListener
            }
            requireActivity().onBackPressed()
            requireActivity().onBackPressed()
        }
    }
}