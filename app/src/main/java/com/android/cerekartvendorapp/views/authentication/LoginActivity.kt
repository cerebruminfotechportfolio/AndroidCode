package com.android.cerekartvendorapp.views.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.databinding.ActivityLoginBinding
import com.android.cerekartvendorapp.preferences.DataManager
import com.android.cerekartvendorapp.viewmodel.LoginSignupViewModel
import com.android.cerekartvendorapp.views.base.BaseActivity
import com.android.cerekartvendorapp.views.home.LandingMainActivity


class LoginActivity : BaseActivity<ActivityLoginBinding>(), View.OnClickListener {
    private lateinit var mBinding: ActivityLoginBinding
    private val mViewModel by lazy { ViewModelProvider(this)[LoginSignupViewModel::class.java] }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
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
        mViewModel.getSignUpResponseLiveData()
            .observe(this) { wrappedResponseEvent ->
                if (wrappedResponseEvent != null && !wrappedResponseEvent.isAlreadyHandled) {
                    val objectWrappedResponse = wrappedResponseEvent.getContent()
                    mViewModel.setLoadingState(false)
                    objectWrappedResponse?.failureResponse?.let {
                        onFailure(it)
                    } ?: let {
                        objectWrappedResponse?.let { respo ->
                            // mViewModel._loadingState.value = false
                            setResult(RESULT_OK)
                            showToastShort(respo.message)
                            finish()
                        }
                    }
                }
            }

        mViewModel.getValidationLiveData().observe(this, {
           when(it.type)
           {
             0->
             {
                 mBinding.etEmail.setError(it.message)
                 mBinding.etEmail.requestFocus()
             }
             1->
             {
                 mBinding.edtPass.setError(it.message)
                 mBinding.edtPass.requestFocus()
             }
           }
        })
        mViewModel.tempResponse.observe(this, {
            DataManager.setUserLoggedinStatus(true)
            startActivity(Intent(this,LandingMainActivity::class.java))

        })
    }

    private fun setClickListeners() {
        mBinding.tvLogin.setOnClickListener(this)
        mBinding.tvForgotPassword.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when (p0) {
            mBinding.tvLogin -> {
                mViewModel.loginRegisterUser()
            }
            mBinding.tvForgotPassword->
            {
                startActivity(Intent(this,ForgotPasswordActivity::class.java))
            }
        }

    }
}