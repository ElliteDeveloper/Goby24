package com.goby24.goby24

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.goby24.goby24.fragments.*
import com.goby24.goby24.globals.SharedPreferenceHelper

class MainActivity : AppCompatActivity() {

    private lateinit var mLlFooterHomeRoot: View
    private lateinit var mLlFooterSearchRoot: View
    private lateinit var mLlFooterOfferRoot: View
    private lateinit var mLlFooterTouristPackageRoot: View
    private lateinit var mLlFooterProfileRoot: View

    private lateinit var mIvFooterHomeIcon: ImageView
    private lateinit var mIvFooterSearchIcon: ImageView
    private lateinit var mIvFooterOfferIcon: ImageView
    private lateinit var mIvFooterTouristPackageIcon: ImageView
    private lateinit var mIvFooterProfileIcon: ImageView

    private lateinit var mTvFooterHomeTitle: TextView
    private lateinit var mTvFooterSearchTitle: TextView
    private lateinit var mTvFooterOfferTitle: TextView
    private lateinit var mTvFooterTouristPackageTitle: TextView
    private lateinit var mTvFooterProfileTitle: TextView

    private var mNPrevActiveFooterIndex = -1

    private val mArrIvFooterIconIds = intArrayOf(
        R.id.iv_main_footer_home,
        R.id.iv_main_footer_search,
        R.id.iv_main_footer_offer,
        R.id.iv_main_footer_tourist_package,
        R.id.iv_main_footer_profile
    )

    private val mArrIvFooterIconActiveDrawableIds = intArrayOf(
        R.drawable.ic_bottom_navi_home_selected,
        R.drawable.ic_bottom_navi_search_selected,
        R.drawable.ic_bottom_navi_offer_selected,
        R.drawable.ic_bottom_navi_packages_selected,
        R.drawable.ic_bottom_navi_profile_selected
    )

    private val mArrIvFooterIconInactiveDrawableIds = intArrayOf(
        R.drawable.ic_bottom_navi_home_normal,
        R.drawable.ic_bottom_navi_search_normal,
        R.drawable.ic_bottom_navi_offer_normal,
        R.drawable.ic_bottom_navi_packages_normal,
        R.drawable.ic_bottom_navi_profile_normal
    )

    private val mArrTvFooterTitleIds = intArrayOf(
        R.id.tv_main_footer_home,
        R.id.tv_main_footer_search,
        R.id.tv_main_footer_offer,
        R.id.tv_main_footer_tourist_package,
        R.id.tv_main_footer_profile
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFooterControls()
        if (intent != null) {
            val strStartupPage = intent.getStringExtra("startup_page");
            if (strStartupPage != null) {
                if (strStartupPage.equals("dashboard")) {
                    addFrag(
                        FragDashboard.newInstance(),
                        -1,
                        -1,
                        false
                    )
                }else if (strStartupPage.equals("profile")) {
                    handleClickOnFooter(4)
                } else {
                    handleClickOnFooter(0)
                }
            } else {
                handleClickOnFooter(0)
            }
        } else {
            handleClickOnFooter(0)
        }
    }


    fun showOrHideFooter(bFlag: Boolean) {
        if (bFlag) {
            findViewById<View>(R.id.rl_main_footer_root).visibility = View.VISIBLE;
        } else {
            findViewById<View>(R.id.rl_main_footer_root).visibility = View.GONE;
        }
    }

    private fun initFooterControls() {
        mLlFooterHomeRoot = findViewById(R.id.ll_main_footer_home)
        mLlFooterSearchRoot = findViewById(R.id.ll_main_footer_search)
        mLlFooterOfferRoot = findViewById(R.id.ll_main_footer_offer)
        mLlFooterTouristPackageRoot = findViewById(R.id.ll_main_footer_tourist_package)
        mLlFooterProfileRoot = findViewById(R.id.ll_main_footer_profile)

        mIvFooterHomeIcon = findViewById(R.id.iv_main_footer_home)
        mIvFooterSearchIcon = findViewById(R.id.iv_main_footer_search)
        mIvFooterOfferIcon = findViewById(R.id.iv_main_footer_offer)
        mIvFooterTouristPackageIcon = findViewById(R.id.iv_main_footer_tourist_package)
        mIvFooterProfileIcon = findViewById(R.id.iv_main_footer_profile)

        mTvFooterHomeTitle = findViewById(R.id.tv_main_footer_home)
        mTvFooterSearchTitle = findViewById(R.id.tv_main_footer_search)
        mTvFooterOfferTitle = findViewById(R.id.tv_main_footer_offer)
        mTvFooterTouristPackageTitle = findViewById(R.id.tv_main_footer_tourist_package)
        mTvFooterProfileTitle = findViewById(R.id.tv_main_footer_profile)

        mLlFooterHomeRoot.setOnClickListener {
            handleClickOnFooter(0)
        }

        mLlFooterSearchRoot.setOnClickListener {
            handleClickOnFooter(1)
        }

        mLlFooterOfferRoot.setOnClickListener {
            handleClickOnFooter(2)
        }

        mLlFooterTouristPackageRoot.setOnClickListener {
            handleClickOnFooter(3)
        }

        mLlFooterProfileRoot.setOnClickListener {
            handleClickOnFooter(4)
        }
    }

    fun checkIfSigned(): Boolean {
        return SharedPreferenceHelper.getLoginStatus(this)
    }

    private fun handleClickOnFooter(index: Int) {
        if (mNPrevActiveFooterIndex == index) return;
        if (index == 0) {
            if (checkIfSigned()) {
                if (mNPrevActiveFooterIndex == -1) {
                    addFrag(
                        FragHome.newInstance(),
                        index,
                        mNPrevActiveFooterIndex,
                        false
                    )
                } else {
                    addFrag(
                        FragHome.newInstance(),
                        index,
                        mNPrevActiveFooterIndex,
                        true
                    )
                }
            } else {
                addFragFromBottom(FragSigninFirst.newInstance(), true)
            }
        } else if (index == 1) {

        } else if (index == 2) {

        } else if (index == 3) {
            if (mNPrevActiveFooterIndex == -1) {
                addFrag(
                    FragTouristPackagesMain.newInstance(),
                    index,
                    mNPrevActiveFooterIndex,
                    false
                )
            } else {
                addFrag(
                    FragTouristPackagesMain.newInstance(),
                    index,
                    mNPrevActiveFooterIndex,
                    true
                )
            }
        } else if (index == 4) {
            if (checkIfSigned()) {
                if (mNPrevActiveFooterIndex == -1) {
                    addFrag(
                        FragProfileStarter.newInstance(),
                        index,
                        mNPrevActiveFooterIndex,
                        false
                    )
                } else {
                    addFrag(
                        FragProfileStarter.newInstance(),
                        index,
                        mNPrevActiveFooterIndex,
                        true
                    )
                }
            }else{
                addFragFromBottom(FragSigninFirst.newInstance(), true)
            }
        }
        toggleFooterIcons(index)
    }

    fun toggleFooterIcons(index: Int) {
        for (n in mArrIvFooterIconIds.indices) {
            if (n == index) {
                findViewById<ImageView>(mArrIvFooterIconIds[n]).setImageResource(
                    mArrIvFooterIconActiveDrawableIds[n]
                )
                findViewById<TextView>(mArrTvFooterTitleIds[n]).setTextColor(resources.getColor(R.color.cyan_light))
            } else {
                findViewById<ImageView>(mArrIvFooterIconIds[n]).setImageResource(
                    mArrIvFooterIconInactiveDrawableIds[n]
                )
                findViewById<TextView>(mArrTvFooterTitleIds[n]).setTextColor(resources.getColor(R.color.txtclr_gray))
            }
        }
        mNPrevActiveFooterIndex = index
    }

    public fun initFrag(frag: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_main_container, frag, null)
            .commit()
    }

    public fun addFrag(frag: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fl_main_container, frag, null)
            .addToBackStack(null)
            .commit()
    }

    public fun addFrag(frag: Fragment, iInIndex: Int, iOutIndex: Int, bAnimation: Boolean) {
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
        ft.replace(R.id.fl_main_container, frag)
        ft.commit()
    }

    public fun addFrag(frag: Fragment, bAnimation: Boolean, bAdd: Boolean) {
        val ft = supportFragmentManager.beginTransaction()
        if (bAnimation) {
            ft.setCustomAnimations(
                R.anim.anim_enter_from_right,
                R.anim.anim_exit_to_left,
                R.anim.anim_enter_from_left,
                R.anim.anim_exit_to_right
            )
        }
        ft.replace(R.id.fl_main_container, frag)
        if (bAdd) {
            ft.addToBackStack(null)
        }
        ft.commit()
    }

    fun addFragFromBottom(frag: Fragment, bFlagToAdd: Boolean) {
        val ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(R.anim.anim_enter_from_bottom, R.anim.anim_exit_to_bottom)
        ft.add(R.id.fl_main_container, frag)
        if (bFlagToAdd) {
            ft.addToBackStack(null);
        }
        ft.commit()
    }
}