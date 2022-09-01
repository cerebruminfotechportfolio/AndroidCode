package com.android.cerekartvendorapp.api

import com.android.cerekartvendorapp.modal.BaseResponse
import com.android.cerekartvendorapp.modal.SignUpLoginResponse
import com.android.cerekartvendorapp.request.LoginSignupRequest
import retrofit2.Call
import retrofit2.http.*

interface ApiClient {
    @POST("user/login")
    fun hitLoginApi(@Body signUpRequest: LoginSignupRequest): Call<BaseResponse<SignUpLoginResponse>>


}