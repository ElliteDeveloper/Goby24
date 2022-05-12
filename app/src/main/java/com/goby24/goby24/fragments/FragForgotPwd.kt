package com.goby24.goby24.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.goby24.goby24.GlobalConstant
import com.goby24.goby24.LoginActivity
import com.goby24.goby24.R

class FragForgotPwd : Fragment() {

    private lateinit var mRootView: View

    companion object {
        fun newInstance(): FragForgotPwd {
            return FragForgotPwd()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mRootView = inflater.inflate(R.layout.frag_forgot_pwd, container, false)
        initView()
        return mRootView
    }

    private fun initView() {
        mRootView.findViewById<View>(R.id.tv_frag_forgot_pwd_continue).setOnClickListener {
            val strEmail: String =
                mRootView.findViewById<EditText>(R.id.ed_frag_forgot_pwd_email).text.toString();
            if (!GlobalConstant.checkEmail(strEmail)) {
                Toast.makeText(requireContext(),resources.getString(R.string.invalid_email), Toast.LENGTH_LONG).show()
                mRootView.findViewById<EditText>(R.id.ed_frag_forgot_pwd_email).requestFocus()
                return@setOnClickListener
            }

            if (requireActivity() is LoginActivity) {
                (requireActivity() as LoginActivity).addFragWithAnimationAndAddBackstack(
                    FragChangePwd.newInstance()
                );
            }
        }
    }
}