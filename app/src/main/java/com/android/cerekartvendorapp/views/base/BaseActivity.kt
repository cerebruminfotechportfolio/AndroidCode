package com.android.cerekartvendorapp.views.base

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.constants.ApiConstants
import com.android.cerekartvendorapp.constants.AppConstants
import com.android.cerekartvendorapp.modal.FailureResponse
import com.android.cerekartvendorapp.preferences.DataManager
import com.android.cerekartvendorapp.utils.LoadingDialog
import com.android.cerekartvendorapp.utils.UtilsFunctions
import com.android.cerekartvendorapp.viewmodel.BaseViewModel
import com.android.cerekartvendorapp.views.authentication.LoginActivity
import com.google.gson.Gson


abstract class BaseActivity<mDataBinding : ViewDataBinding> : AppCompatActivity() {
    private lateinit var mViewDataBinding: mDataBinding
    private var progressDialog: LoadingDialog? = null
    private var baseViewModel: BaseViewModel? = null
    private val gson=Gson()

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
    }

    open fun getViewDataBinding(): mDataBinding {
        return mViewDataBinding
    }

    /**
     * change status bar colour
     */
    open fun setStatusBarColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }
    }

    /**
     * change status bar colour
     */
    open fun setLightStatusBar(view: View, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = view.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            view.systemUiVisibility = flags
            window.statusBarColor = color
        }
    }

    /**
     * Remove status bar for using full screen
     */
    open fun setFullScreen() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        window.statusBarColor = Color.TRANSPARENT
    }

    /**
     * Show text for long duration
     *
     * @param message message contained in toast
     */
    open fun showToastLong(message: CharSequence?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    /**
     * Show text for short duration
     * @param message message contained in toast
     */
    open fun showToastShort(message: CharSequence?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * showing the progress dialog
     */
    open fun showProgressDialog() {
        if (!isDestroyed) {
            progressDialog = LoadingDialog(this)
            progressDialog?.show()

        }
    }

    /**
     * hiding the progress dialog
     */
    open fun hideProgressDialog() {
        progressDialog?.let {
            if (it.isShowing)
                it.dismiss()
        }
    }

    /**
     *
     * @param viewModel setting up the any [androidx.lifecycle.ViewModel] extending [BaseViewModel]
     * The main purpose to access any functionality for [BaseViewModel] like showing and hiding [LoadingDialog]
     *
     */
    open fun setBaseViewModel(viewModel: BaseViewModel) {
        this.baseViewModel = viewModel
        baseViewModel?.loadingState?.observe(this, Observer { loadingState ->
            if (loadingState)
                    showProgressDialog()
                else
                    hideProgressDialog()

        })
    }

    open fun saveDeviceToken() {
        DataManager.saveDeviceToken(UtilsFunctions.getUniqueDeviceId(this))
    }
    fun onFailure(failureResponse: FailureResponse) {
        if (failureResponse.errorCode == ApiConstants.SESSION_EXPIRED_ERROR_CODE)
         {
               logout()
        } else {
            failureResponse.errorMessage?.let { showToastShort(it) }
        }
    }

    fun logout() {
        val isRememberMe=DataManager.isRememberMe()
        DataManager.setRememberedMe(isRememberMe)
        gotoLogInScreen()
    }

     fun gotoLogInScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
    fun callFragments(
        fragment: androidx.fragment.app.Fragment?,
        mFragmentManager: androidx.fragment.app.FragmentManager,
        mSendDataCheck: Boolean,
        key: String?,
        mObject: Any
    ) {
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        if (fragment != null) {
            if (mSendDataCheck && key != null) {
                when (key) {
                    "deleteAll" -> mFragmentManager.popBackStack(
                        null,
                        androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    "send_data" -> {
                        val mBundle = Bundle()
                        mBundle.putString(AppConstants.SEND_DATA, gson.toJson(mObject))
                        fragment.arguments = mBundle
                    }

                }
            }
            mFragmentTransaction.addToBackStack(null)
            mFragmentTransaction.replace(R.id.frame_layout, fragment)
            mFragmentTransaction.commit()
        }
    }
}