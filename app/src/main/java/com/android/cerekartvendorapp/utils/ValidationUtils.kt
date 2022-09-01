package com.android.cerekartvendorapp.utils

import java.util.regex.Pattern


object ValidationUtils {

    @JvmStatic
     fun isEmailValid(emailId: String): Boolean {
         val emailPattern = ("[A-Z0-9a-z.-_+]+[A-Z0-9a-z]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,50}")
         return if (emailId.length < 3 || emailId.length > 265) false else {
             emailId.matches(Regex(emailPattern))
         }
     }

   /* @JvmStatic
    fun isPasswordValid(password: String): Boolean {
        val passwordREGEX = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#?!@$%^&*-])[A-Za-z\\d#?!@$%^&*-]{8,16}$"
        )
        return passwordREGEX.matcher(password).matches()
    }*/

    @JvmStatic
    fun isPasswordValid(password: String): Boolean {
       if(password.length<8)
           return false
        return true
    }
}