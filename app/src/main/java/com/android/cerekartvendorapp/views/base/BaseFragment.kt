package com.android.cerekartvendorapp.views.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.android.cerekartvendorapp.viewmodel.BaseViewModel
import com.android.cerekartvendorapp.modal.FailureResponse

abstract class BaseFragment<MyDataBinding : ViewDataBinding> : Fragment() {

    private lateinit var mViewDataBinding: MyDataBinding
    private lateinit var mRootView: View
    private var baseViewModel: BaseViewModel? = null

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mRootView = mViewDataBinding.root
        return mRootView
    }

    fun onFailure(failureResponse: FailureResponse) {
        (activity as? BaseActivity<*>)?.onFailure(failureResponse)
    }

    fun logout() {
        (activity as? BaseActivity<*>)?.logout()
    }
    protected fun setFragmentBaseViewModel(viewModel: BaseViewModel){
        this.baseViewModel = viewModel
        baseViewModel?.loadingState?.observe(viewLifecycleOwner, Observer { loadingState ->
            if (loadingState)
                showProgressDialog()
            else
                hideProgressDialog()

        })
    }

    open fun getViewDataBinding(): MyDataBinding {
        return mViewDataBinding
    }

    fun showToastLong(message: String) {
        (activity as? BaseActivity<*>)?.showToastLong(message)
    }

    fun showToastShort(message: String) {
        (activity as? BaseActivity<*>)?.showToastShort(message)
    }

    private fun showProgressDialog() {
        (activity as? BaseActivity<*>)?.showProgressDialog()
    }

    private fun hideProgressDialog() {
        (activity as? BaseActivity<*>)?.hideProgressDialog()
    }


    override fun onStop() {
        hideProgressDialog()
        super.onStop()
    }
}