package com.android.cerekartvendorapp.request


import com.google.gson.annotations.SerializedName

data class LoginSignupRequest(
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("fullName")
    var fullName: String? = null,
    @SerializedName("deviceId")
    var deviceId: String? = null,
    @SerializedName("deviceToken")
    var deviceToken: String? = null,
    @SerializedName("password")
    var password: String? = null
)