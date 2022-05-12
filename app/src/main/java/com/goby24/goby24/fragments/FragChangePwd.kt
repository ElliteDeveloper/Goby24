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
import com.goby24.goby24.R

class FragChangePwd : Fragment() {

    private lateinit var mRootView: View
    private var mFlagShowPwdStatusNew = false;
    private var mFlagShowPwdStatusConfirm = false;

    companion object {
        fun newInstance(): FragChangePwd {
            return FragChangePwd()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(R.layout.frag_forgot_pwd_change_pwd, container, false)
        initView()
        return mRootView
    }

    @SuppressLint("CutPasteId")
    private fun initView() {
        mRootView.findViewById<View>(R.id.tv_frag_forgot_pwd_change_pwd_reset).setOnClickListener {
            val strNewPwd: String =
                mRootView.findViewById<EditText>(R.id.ed_frag_forgot_pwd_change_pwd_new).text.toString();
            val strConfirmPassword: String =
                mRootView.findViewById<EditText>(R.id.ed_frag_forgot_pwd_change_pwd_confirm).text.toString();

            if (strNewPwd.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.empty_password),
                    Toast.LENGTH_LONG
                ).show()
                mRootView.findViewById<EditText>(R.id.ed_frag_forgot_pwd_change_pwd_new)
                    .requestFocus()
                return@setOnClickListener
            }

            if (strConfirmPassword.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.empty_confirm_password),
                    Toast.LENGTH_LONG
                ).show()
                mRootView.findViewById<EditText>(R.id.ed_frag_forgot_pwd_change_pwd_confirm)
                    .requestFocus()
                return@setOnClickListener
            }

            if (!strNewPwd.equals(strConfirmPassword)) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.pwd_not_match),
                    Toast.LENGTH_LONG
                ).show()
                mRootView.findViewById<EditText>(R.id.ed_frag_forgot_pwd_change_pwd_new)
                    .requestFocus()
                return@setOnClickListener
            }

            requireActivity().onBackPressed()
            requireActivity().onBackPressed()
        }

        mRootView.findViewById<View>(R.id.iv_frag_forgot_pwd_change_pwd_new).setOnClickListener {
            if (mFlagShowPwdStatusNew) {
                mRootView.findViewById<EditText>(R.id.ed_frag_forgot_pwd_change_pwd_new).transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                mRootView.findViewById<ImageView>(R.id.iv_frag_forgot_pwd_change_pwd_new).setImageResource(R.drawable.hide_password)
            } else {
                mRootView.findViewById<EditText>(R.id.ed_frag_forgot_pwd_change_pwd_new).transformationMethod =
                    PasswordTransformationMethod.getInstance()
                mRootView.findViewById<ImageView>(R.id.iv_frag_forgot_pwd_change_pwd_new).setImageResource(R.drawable.show_password)
            }
            mFlagShowPwdStatusNew = !mFlagShowPwdStatusNew
        }

        mRootView.findViewById<View>(R.id.iv_frag_forgot_pwd_change_pwd_confirm).setOnClickListener {
            if (mFlagShowPwdStatusConfirm) {
                mRootView.findViewById<EditText>(R.id.ed_frag_forgot_pwd_change_pwd_confirm).transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                mRootView.findViewById<ImageView>(R.id.iv_frag_forgot_pwd_change_pwd_confirm).setImageResource(R.drawable.hide_password)
            } else {
                mRootView.findViewById<EditText>(R.id.ed_frag_forgot_pwd_change_pwd_confirm).transformationMethod =
                    PasswordTransformationMethod.getInstance()
                mRootView.findViewById<ImageView>(R.id.iv_frag_forgot_pwd_change_pwd_confirm).setImageResource(R.drawable.show_password)
            }
            mFlagShowPwdStatusConfirm = !mFlagShowPwdStatusConfirm
        }
    }
}