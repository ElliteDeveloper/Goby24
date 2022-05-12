package com.goby24.goby24.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.goby24.goby24.MainActivity
import com.goby24.goby24.R
import com.goby24.goby24.ui.SpinnerAdapterPhonePrefix
import com.goby24.goby24.ui.SpinnerAdapterPlateNumber

class FragAddCarPlateNumber: Fragment() {

    private lateinit var mRootView:View
    private lateinit var mEdPlateNumber:EditText
    private lateinit var mSpPhonePrefix:Spinner

    companion object {
        fun newInstance(): FragAddCarPlateNumber {
            return FragAddCarPlateNumber()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(R.layout.frag_add_car_plate_number, container, false)
        initView()
        return mRootView
    }

    private fun initView(){
        mEdPlateNumber = mRootView.findViewById(R.id.ed_frag_add_car_plate_number)
        mSpPhonePrefix = mRootView.findViewById(R.id.sp_frag_add_car_plate_number_prefix);
        mSpPhonePrefix.adapter = SpinnerAdapterPlateNumber(requireContext())

        mRootView.findViewById<View>(R.id.iv_frag_add_car_plate_number_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        mRootView.findViewById<View>(R.id.tv_frag_add_car_plate_number_continue).setOnClickListener {
            val strFirstName:String = mEdPlateNumber.text.toString()
            if (strFirstName.isEmpty()){
                Toast.makeText(requireActivity(), R.string.enter_car_plate_number, Toast.LENGTH_LONG).show()
                mEdPlateNumber.requestFocus()
                return@setOnClickListener
            }
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).addFrag(FragVehicleInfo.newInstance(), true, true)
            }
        }

        mRootView.findViewById<View>(R.id.tv_frag_add_car_plate_number_dont_know).setOnClickListener {
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).addFrag(FragVehicleInfo.newInstance(), true, true)
            }
        }
    }
}