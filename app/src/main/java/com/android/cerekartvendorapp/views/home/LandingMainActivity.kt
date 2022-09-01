package com.android.cerekartvendorapp.views.home


import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.constants.AppConstants
import com.android.cerekartvendorapp.databinding.ActivityLandingBinding
import com.android.cerekartvendorapp.views.base.BaseActivity
import com.android.cerekartvendorapp.views.products.ProductsFragment
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView


class LandingMainActivity : BaseActivity<ActivityLandingBinding>(), View.OnClickListener {
    private var navigationView: NavigationView? = null
    private var drawer: DrawerLayout? = null
    var fragment: Fragment? = null
    private lateinit var mBinding: ActivityLandingBinding

    override fun getLayoutId(): Int {
        return R.layout.activity_landing
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = getViewDataBinding()
        setClickListeners()
        setUpData()
    }

    private fun setClickListeners() {
        mBinding.rlTop.setOnClickListener(this)
        mBinding.linProducts.setOnClickListener(this)
        mBinding.imgNavCancel.setOnClickListener(this)
        mBinding.topView.imgeNavigation.setOnClickListener(this)
    }


    private fun setUpData() {
        navigationView = mBinding.navView
        drawer = mBinding.drawerLayout
        Glide.with(this)
            .load(R.drawable.ic_user)
            .placeholder(R.drawable.ic_user)
            .into(mBinding.icProfile)
        fragment = HomeFragment()
        callFragments(fragment, supportFragmentManager, false, AppConstants.SEND_DATA, "")
    }

    fun openCloseDrawer() {
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawer(Gravity.LEFT) //CLOSE Nav Drawer!
        } else {
            drawer!!.openDrawer(Gravity.LEFT)
        }

    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    override fun onClick(p0: View?) {
        when (p0) {
            mBinding.imgNavCancel, mBinding.topView.imgeNavigation -> {
                openCloseDrawer()

            }
            mBinding.rlTop -> {
                val fragment = HomeFragment()

                mBinding.drawerLayout.closeDrawers()
                this.callFragments(
                    fragment,
                    supportFragmentManager,
                    false,
                    AppConstants.SEND_DATA,
                    ""
                )

            }
            mBinding.linProducts -> {
                val fragment = ProductsFragment()

                mBinding.drawerLayout.closeDrawers()
                this.callFragments(
                    fragment,
                    supportFragmentManager,
                    false,
                    AppConstants.SEND_DATA,
                    ""
                )
            }
        }
    }
}