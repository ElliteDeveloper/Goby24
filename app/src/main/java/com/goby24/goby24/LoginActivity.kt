package com.goby24.goby24

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.goby24.goby24.fragments.FragSignin

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initFrag(FragSignin.newInstance())
    }

    private fun initFrag(frag: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_login_container, frag, null)
            .commit()
    }

    fun addFrag(frag: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fl_login_container, frag, null)
            .addToBackStack(null)
            .commit()
    }

    fun addFragWithAnimation(frag: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .setCustomAnimations(R.anim.anim_enter_from_right, R.anim.anim_exit_to_left, R.anim.anim_enter_from_left, R.anim.anim_exit_to_right)
            .replace(R.id.fl_login_container, frag, null)
            .commit()
    }

    fun addFragWithAnimationAndAddBackstack(frag: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .setCustomAnimations(R.anim.anim_enter_from_right, R.anim.anim_exit_to_left, R.anim.anim_enter_from_left, R.anim.anim_exit_to_right)
            .replace(R.id.fl_login_container, frag, null)
            .commit()
    }

    fun addFrag(frag: Fragment, iInIndex:Int, iOutIndex:Int, bAnimation:Boolean) {
        val ft = supportFragmentManager.beginTransaction()
        for (i in 0 until supportFragmentManager.backStackEntryCount) {
            supportFragmentManager.popBackStack()
        }
        if (bAnimation) {
            if (iInIndex > iOutIndex) {
                ft.setCustomAnimations(R.anim.anim_enter_from_right, R.anim.anim_exit_to_left)
            } else {
                ft.setCustomAnimations(R.anim.anim_enter_from_left, R.anim.anim_exit_to_right)
            }
        }
        ft.replace(R.id.fl_login_container, frag)
        ft.commit()
    }
}