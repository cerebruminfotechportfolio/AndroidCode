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


class AddProductViewModel : BaseViewModel() {
    var mProdQuantity = ObservableField<String>()
    var mProdName = ObservableField<String>()
    var mProdDesc = ObservableField<String>()
    var mDuration = ObservableField<String>()
    var mPrice = ObservableField<String>()
    var itemType:Int=-1
    var unit:Int=-1
    var isStockManagement=false



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
    fun addProduct() {
        if (checkValidation()) {
            setLoadingState(true)
           // signUpRequestLiveData.value = signupRequest
            setTempResponse(true)
        }
    }

    private fun checkValidation(): Boolean {

      if(isStockManagement)
      {
          if (mProdQuantity.get().isNullOrBlank()) {
              mValidationLiveData.value = DataValidation(
                  CereKartVendorApp.instance.getString(
                      R.string.field_required
                  ),0
              )
              return false
          }
      }
        if (mProdName.get().isNullOrBlank()) {
                mValidationLiveData.value = DataValidation(
                    CereKartVendorApp.instance.getString(
                        R.string.field_required
                    )
                ,1)
                return false
            }

        if (itemType==-1) {
            mValidationLiveData.value = DataValidation(
                CereKartVendorApp.instance.getString(
                    R.string.field_required
                )
                ,2)
            return false
        }
        if (mProdDesc.get().isNullOrBlank()) {
            mValidationLiveData.value = DataValidation(
                CereKartVendorApp.instance.getString(
                    R.string.field_required
                )
                ,3)
            return false
        }
        if (unit==-1) {
            mValidationLiveData.value = DataValidation(
                CereKartVendorApp.instance.getString(
                    R.string.field_required
                )
                ,4)
            return false
        }
        if (mDuration.get().isNullOrBlank()) {
            mValidationLiveData.value = DataValidation(
                CereKartVendorApp.instance.getString(
                    R.string.field_required
                )
                ,5)
            return false
        }
        if (mPrice.get().isNullOrBlank()) {
            mValidationLiveData.value = DataValidation(
                CereKartVendorApp.instance.getString(
                    R.string.field_required
                )
                ,6)
            return false
        }
        return true

    }

}