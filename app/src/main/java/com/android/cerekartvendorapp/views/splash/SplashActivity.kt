package com.tracket.views.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.databinding.ActivitySplashBinding
import com.android.cerekartvendorapp.preferences.DataManager
import com.android.cerekartvendorapp.views.authentication.LoginActivity
import com.android.cerekartvendorapp.views.base.BaseActivity
import com.android.cerekartvendorapp.views.home.LandingMainActivity


class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private lateinit var mBinding: ActivitySplashBinding

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = getViewDataBinding()

        Handler(Looper.getMainLooper()).postDelayed({
           if(!DataManager.isUserLoggedIn())
            startActivity(Intent(this, LoginActivity::class.java))
           else
               startActivity(Intent(this, LandingMainActivity::class.java))

        }, 3000)

    }
}