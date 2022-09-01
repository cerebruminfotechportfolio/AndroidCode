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
import com.android.cerekartvendorapp.repo.LoginSignupRepo
import com.android.cerekartvendorapp.request.LoginSignupRequest
import com.android.cerekartvendorapp.utils.ValidationUtils


class ForgotPasswordViewModel : BaseViewModel() {
    var mEmailEditText = ObservableField<String>()
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
     * Api call for reset password
     */
    fun resetPassword() {
        if (checkValidation()) {
            val signupRequest = LoginSignupRequest()
            signupRequest.email = mEmailEditText.get()
            setLoadingState(true)
            setTempResponse(true)
        }
    }

    private fun checkValidation(): Boolean {

        if (mEmailEditText.get().isNullOrBlank()) {
                mValidationLiveData.value = DataValidation(
                    CereKartVendorApp.instance.getString(
                        R.string.please_enter_email_address
                    )
                )
                return false
            } else if (!ValidationUtils.isEmailValid(mEmailEditText.get().toString().trim())) {
                mValidationLiveData.value = DataValidation(
                    CereKartVendorApp.instance.getString(
                        R.string.please_enter_valid_email_address
                    )
                )
                return false
            }

        return true

    }

}