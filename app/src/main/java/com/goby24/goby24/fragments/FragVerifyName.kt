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

class FragVerifyName: Fragment() {

    private lateinit var mRootView:View
    private lateinit var mEdFirstName:EditText
    private lateinit var mEdLastName:EditText

    companion object {
        fun newInstance(): FragVerifyName {
            return FragVerifyName()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(R.layout.frag_verify_name, container, false)
        initView()
        return mRootView
    }

    private fun initView(){
        mEdFirstName = mRootView.findViewById(R.id.ed_frag_verify_name_firstname)
        mEdLastName = mRootView.findViewById(R.id.ed_frag_verify_name_lastname)

        mRootView.findViewById<View>(R.id.iv_frag_verify_name_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        mRootView.findViewById<View>(R.id.tv_frag_verify_name_continue).setOnClickListener {
            val strFirstName:String = mEdFirstName.text.toString()
            val strLastName:String = mEdLastName.text.toString()
            if (strFirstName.isEmpty()){
                Toast.makeText(requireActivity(), R.string.enter_first_name, Toast.LENGTH_LONG).show()
                mEdFirstName.requestFocus()
                return@setOnClickListener
            }
            if (strLastName.isEmpty()){
                Toast.makeText(requireActivity(), R.string.enter_last_name, Toast.LENGTH_LONG).show()
                mEdLastName.requestFocus()
                return@setOnClickListener
            }
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).addFrag(FragVerifyID.newInstance(), true, true)
            }
        }
    }
}