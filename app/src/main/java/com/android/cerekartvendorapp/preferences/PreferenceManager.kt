package com.android.cerekartvendorapp.preferences

import android.content.Context
import android.content.SharedPreferences
import com.android.cerekartvendorapp.CereKartVendorApp

object PreferenceManager {
    private const val PREF_NAME="Cerevendor"
    const val DEVICE_TOKEN = "device_token"
    const val DEVICE_ID = "device_id"
    const val USER_DETAILS = "user_detail"
    const val IS_REMEMBER_ME="is_remember_me"
    const val IS_USER_LOGGED_IN="is_user_logged_in"
    const val ACCESS_TOKEN = "access_token"


    private var sharedPref: SharedPreferences

    init {
        sharedPref =
          CereKartVendorApp.instance.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
    }

    fun getInt(key: String?): Int {
        return sharedPref.getInt(key, 0)
    }

    fun putInt(key: String?, value: Int) {
        val editor = sharedPref.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun putString(key: String?, value: String?) {
        val editor = sharedPref.edit()
        if (value != null) {
            editor.putString(key, value) // Commit the edits!
            editor.apply()
        }
    }

    fun getString(key: String?) = sharedPref.getString(key, "")

    fun putBoolean(key: String?, value: Boolean) {
        val editor = sharedPref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String?) = sharedPref.getBoolean(key, false)

    fun clearAllPrefs() {
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }


}