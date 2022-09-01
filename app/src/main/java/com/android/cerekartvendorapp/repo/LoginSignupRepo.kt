package com.android.cerekartvendorapp.repo

import androidx.lifecycle.MutableLiveData
import com.android.cerekartvendorapp.api.ApiManager
import com.android.cerekartvendorapp.api.NetworkCallback
import com.android.cerekartvendorapp.modal.*
import com.android.cerekartvendorapp.request.LoginSignupRequest

import retrofit2.Call

class LoginSignupRepo {

    private var call: Call<BaseResponse<SignUpLoginResponse>>?=null


    internal fun hitLoginSignupApi(signupRequest: LoginSignupRequest): MutableLiveData<Event<WrappedResponse<SignUpLoginResponse>>> {

        val socialLoginResponseLiveData =
            MutableLiveData<Event<WrappedResponse<SignUpLoginResponse>>>()
        val wrappedResponse = WrappedResponse<SignUpLoginResponse>()

        call= ApiManager.hitLoginApi(signupRequest)



      call?.enqueue(object : NetworkCallback<SignUpLoginResponse>() {
                override fun onSuccess(
                    t: SignUpLoginResponse?,
                    message: String
                ) {
                    wrappedResponse.data = t
                    socialLoginResponseLiveData.value = Event(wrappedResponse)
                }

                override fun onFailure(failureResponse: FailureResponse) {
                    wrappedResponse.failureResponse = failureResponse
                    socialLoginResponseLiveData.value = Event(wrappedResponse)
                }

                override fun onError(t: Throwable) {
                    wrappedResponse.failureResponse = FailureResponse.genericError()
                    socialLoginResponseLiveData.value = Event(wrappedResponse)
                }
            })

        return socialLoginResponseLiveData
    }
}