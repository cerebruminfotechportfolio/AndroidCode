package com.android.cerekartvendorapp.views.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.databinding.ActivityForgotPasswordBinding
import com.android.cerekartvendorapp.databinding.ActivityLoginBinding
import com.android.cerekartvendorapp.viewmodel.ForgotPasswordViewModel
import com.android.cerekartvendorapp.viewmodel.LoginSignupViewModel
import com.android.cerekartvendorapp.views.base.BaseActivity


class ForgotPasswordActivity : BaseActivity<ActivityForgotPasswordBinding>(), View.OnClickListener {
    private lateinit var mBinding: ActivityForgotPasswordBinding
    private val mViewModel by lazy { ViewModelProvider(this)[ForgotPasswordViewModel::class.java] }

    override fun getLayoutId(): Int {
        return R.layout.activity_forgot_password
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = getViewDataBinding()
        mBinding.model = mViewModel
        setClickListeners()
        setObserver()

    }


    private fun setObserver() {
        setBaseViewModel(mViewModel)

        mViewModel.getValidationLiveData().observe(this, {
            showToastShort(it.message)
        })
        mViewModel.tempResponse.observe(this, {
            showToastShort("Success")
        })
    }

    private fun setClickListeners() {
        mBinding.tvResetPass.setOnClickListener(this)
        mBinding.tvBackLogin.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when (p0) {
            mBinding.tvResetPass -> {
                mViewModel.resetPassword()
            }
            mBinding.tvBackLogin->
            {
                finish()
            }
        }

    }
}