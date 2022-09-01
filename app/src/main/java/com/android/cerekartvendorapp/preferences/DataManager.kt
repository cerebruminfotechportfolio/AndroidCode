package com.android.cerekartvendorapp.preferences

import com.android.cerekartvendorapp.modal.SignUpLoginResponse
import com.google.gson.Gson


object DataManager {

    fun getDeviceToken(): String {
        var deviceToken = ""
        PreferenceManager.getString(PreferenceManager.DEVICE_TOKEN)?.let {
            deviceToken = it
        }
        return deviceToken
    }

    fun saveDeviceToken(deviceToken: String?) {
        PreferenceManager.putString(PreferenceManager.DEVICE_TOKEN, deviceToken)
    }

    fun getDeviceId(): String? {
        return PreferenceManager.getString(PreferenceManager.DEVICE_ID)
    }

    fun getAccessToken(): String? {
        return PreferenceManager.getString(PreferenceManager.ACCESS_TOKEN)
    }

    fun saveAccessToken(accessToken: String?) {
        PreferenceManager.putString(PreferenceManager.ACCESS_TOKEN, accessToken)
    }
    fun saveUserDetails(user: SignUpLoginResponse?) {
        val userDetail = Gson().toJson(user)
        PreferenceManager.putString(PreferenceManager.USER_DETAILS, userDetail)
    }

    fun getUserDetails(): SignUpLoginResponse? {
        val userDetail: String? =
            PreferenceManager.getString(PreferenceManager.USER_DETAILS)
        return Gson().fromJson(userDetail, SignUpLoginResponse::class.java)
    }




    fun isRememberMe(): Boolean {
        return PreferenceManager.getBoolean(PreferenceManager.IS_REMEMBER_ME)
    }

    fun setRememberedMe(status: Boolean) {
        PreferenceManager.putBoolean(PreferenceManager.IS_REMEMBER_ME, status)
    }

    fun isUserLoggedIn(): Boolean {
        return PreferenceManager.getBoolean(PreferenceManager.IS_USER_LOGGED_IN)
    }

    fun setUserLoggedinStatus(status: Boolean) {
        PreferenceManager.putBoolean(PreferenceManager.IS_USER_LOGGED_IN, status)
    }

    fun clearPreferences() {
        PreferenceManager.clearAllPrefs()
    }
}