package com.android.cerekartvendorapp.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.android.cerekartvendorapp.CereKartVendorApp
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.api.DataValidation
import com.android.cerekartvendorapp.modal.Event
import com.android.cerekartvendorapp.modal.SignUpLoginResponse
import com.android.cerekartvendorapp.modal.WrappedResponse
import com.android.cerekartvendorapp.preferences.DataManager
import com.android.cerekartvendorapp.repo.LoginSignupRepo
import com.android.cerekartvendorapp.request.LoginSignupRequest
import com.android.cerekartvendorapp.utils.ValidationUtils


class LoginSignupViewModel : BaseViewModel() {
    var mEmailEditText = ObservableField<String>()
    var mPasswordText = ObservableField<String>()
    private val repo = LoginSignupRepo()
    private val signUpRequestLiveData = MutableLiveData<LoginSignupRequest>()
    private val signUpResponseLiveData =
        Transformations.switchMap(signUpRequestLiveData) { request ->
            repo.hitLoginSignupApi(request)
        }

    fun getSignUpResponseLiveData(): LiveData<Event<WrappedResponse<SignUpLoginResponse>>> {
        return signUpResponseLiveData
    }

    /**
     * Api call for register and login activity
     */
    fun loginRegisterUser() {
        if (checkValidation()) {
            val signupRequest = LoginSignupRequest()
            signupRequest.email = mEmailEditText.get()
            signupRequest.password = mPasswordText.get()
            signupRequest.deviceId = DataManager.getDeviceId()
            signupRequest.deviceToken = DataManager.getDeviceToken()
            setLoadingState(true)
           // signUpRequestLiveData.value = signupRequest
            setTempResponse(true)
        }
    }

    private fun checkValidation(): Boolean {

        if (mEmailEditText.get().isNullOrBlank()) {
                mValidationLiveData.value = DataValidation(
                    CereKartVendorApp.instance.getString(
                        R.string.please_enter_email_address
                    ),0
                )
                return false
            } else if (!ValidationUtils.isEmailValid(mEmailEditText.get().toString().trim())) {
                mValidationLiveData.value = DataValidation(
                    CereKartVendorApp.instance.getString(
                        R.string.please_enter_valid_email_address
                    )
                ,0)
                return false
            } else if (mPasswordText.get().isNullOrBlank()) {
                mValidationLiveData.value = DataValidation(
                    CereKartVendorApp.instance.getString(
                        R.string.please_enter_password
                    )
               ,1 )
                return false
            } /*else if (!ValidationUtils.isPasswordValid(mPasswordText.get() ?: "")) {
                mValidationLiveData.value = DataValidation(
                    CereKartVendorApp.instance.getString(
                        R.string.please_enter_password_valid
                    )
                )
                return false
            }*/

        return true

    }

}