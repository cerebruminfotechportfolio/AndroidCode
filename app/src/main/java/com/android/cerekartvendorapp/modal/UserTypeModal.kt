package com.android.cerekartvendorapp.modal

data class UserTypeModal(var email:String,var type:String,var isSelected:Boolean =false,var name:String="")
data class ReminderOptions(
    var option:String?=null,
    var isSelected:Boolean =false)