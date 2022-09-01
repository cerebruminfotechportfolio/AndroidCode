package com.android.cerekartvendorapp.modal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(

    @SerializedName("statusCode")
    @Expose
    var code: Int? = null,
    @SerializedName("message")
    @Expose
    val message: String = "",
    @SerializedName("data")
    @Expose
    val data: T? = null
)