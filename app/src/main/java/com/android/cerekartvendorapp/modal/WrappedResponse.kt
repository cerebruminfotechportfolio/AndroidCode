package com.android.cerekartvendorapp.modal


data class WrappedResponse<T>(
    var data: T? = null,
    var message: String? = null,
    var failureResponse: FailureResponse? = null
)