package com.goby24.goby24.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.goby24.goby24.GlobalConstant
import com.goby24.goby24.LoginActivity
import com.goby24.goby24.MainActivity
import com.goby24.goby24.R

class FragCurPwd : Fragment() {

    private lateinit var mRootView: View
    private var mFlagShowPwdStatusNew = false

    companion object {
        fun newInstance(): FragCurPwd {
            return FragCurPwd()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(R.layout.frag_current_pwd, container, false)
        initView()
        return mRootView
    }

    @SuppressLint("CutPasteId")
    private fun initView() {
        mRootView.findViewById<View>(R.id.iv_frag_current_pwd_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        mRootView.findViewById<View>(R.id.tv_frag_current_pwd_confirm).setOnClickListener {
            val strNewPwd: String = mRootView.findViewById<EditText>(R.id.ed_frag_current_pwd_password).text.toString()

            if (strNewPwd.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.empty_password),
                    Toast.LENGTH_LONG
                ).show()
                mRootView.findViewById<EditText>(R.id.ed_frag_current_pwd_password).requestFocus()
                return@setOnClickListener
            }

            if (requireActivity() is MainActivity){
                (requireActivity() as MainActivity).addFrag(FragChangePwd.newInstance(), true, true)
            }
        }

        mRootView.findViewById<View>(R.id.iv_frag_current_pwd_toggle_password).setOnClickListener {
            if (mFlagShowPwdStatusNew) {
                mRootView.findViewById<EditText>(R.id.ed_frag_current_pwd_password).transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                mRootView.findViewById<ImageView>(R.id.iv_frag_current_pwd_toggle_password).setImageResource(R.drawable.hide_password)
            } else {
                mRootView.findViewById<EditText>(R.id.ed_frag_current_pwd_password).transformationMethod =
                    PasswordTransformationMethod.getInstance()
                mRootView.findViewById<ImageView>(R.id.iv_frag_current_pwd_toggle_password).setImageResource(R.drawable.show_password)
            }
            mFlagShowPwdStatusNew = !mFlagShowPwdStatusNew
        }
    }
}