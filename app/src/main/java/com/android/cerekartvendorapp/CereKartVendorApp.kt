package com.android.cerekartvendorapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.android.cerekartvendorapp.utils.FontStyle


class CereKartVendorApp : Application() {
    private var customFontFamily: FontStyle? = null

    override fun onCreate() {
        super.onCreate()
        instance=this
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        customFontFamily = FontStyle.instance
        customFontFamily!!.addFont("regular", "Poppins-Regular.ttf")
        customFontFamily!!.addFont("semibold", "Poppins-SemiBold.ttf")
        customFontFamily!!.addFont("bold", "Poppins-SemiBold.ttf")
        customFontFamily!!.addFont("betty", "Sixty_Nine_Demo.ttf")
    }

    companion object
    {
        lateinit var instance:CereKartVendorApp
    }
}