package com.android.cerekartvendorapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.cerekartvendorapp.api.DataValidation


/**
 * All the ViewModel classes will be extending this class
 *
 */
open class BaseViewModel : ViewModel() {

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    val mValidationLiveData by lazy { MutableLiveData<DataValidation>() }

    fun getValidationLiveData() = mValidationLiveData

    fun setLoadingState(state: Boolean) {
        _loadingState.value = state
    }
    private val _tempResp = MutableLiveData<Boolean>()

    fun setTempResponse(state: Boolean) {
        _tempResp.value = state
    }
    val tempResponse: LiveData<Boolean>
        get() = _tempResp
}