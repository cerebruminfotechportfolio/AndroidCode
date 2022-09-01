package com.android.cerekartvendorapp.modal

import com.google.gson.annotations.SerializedName

data class SignUpLoginResponse(
    @SerializedName("accessToken")
var accessToken: String? = null,
@SerializedName("userId")
var userId: String? = null,
@SerializedName("email")
var email: String? = null, @SerializedName("profilePicture")
    var profilePicture: String? = null,
@SerializedName("userType")
var userType: String? = null,
@SerializedName("fullName")
var fullName: String? = null
)
