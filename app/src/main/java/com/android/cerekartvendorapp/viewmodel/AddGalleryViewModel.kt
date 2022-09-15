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


class AddGalleryViewModel : BaseViewModel() {
    var mTitle = ObservableField<String>()
    var mDesc = ObservableField<String>()
    var itemType:Int=-1




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
     * Api call for add product
     */
    fun addGallery() {
        if (checkValidation()) {
            setLoadingState(true)
           // signUpRequestLiveData.value = signupRequest
            setTempResponse(true)
        }
    }

    private fun checkValidation(): Boolean {
        if (mTitle.get().isNullOrBlank()) {
                mValidationLiveData.value = DataValidation(
                    CereKartVendorApp.instance.getString(
                        R.string.field_required
                    )
                ,0)
                return false
            }

         if(mTitle.get().toString().length<2)
        {
            mValidationLiveData.value = DataValidation(
                CereKartVendorApp.instance.getString(
                    R.string.image_name_length
                )
                ,0)
            return false
        }

        if (mDesc.get().isNullOrBlank()) {
            mValidationLiveData.value = DataValidation(
                CereKartVendorApp.instance.getString(
                    R.string.field_required
                )
                ,2)
            return false
        }

        if(mDesc.get().toString().length<40)
        {
            mValidationLiveData.value = DataValidation(
                CereKartVendorApp.instance.getString(
                    R.string.image_desc_name_length
                )
                ,2)
            return false
        }

        return true

    }

}