package com.android.cerekartvendorapp.modal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CommonResponse(

    @SerializedName("statusCode")
    @Expose
    var code: Int? = null,
    @SerializedName("message")
    @Expose
    var message: String = "",

)