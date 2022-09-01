package com.android.cerekartvendorapp.callbacks


/*
* This class used to listing every click or any other event from the RecyclerViewAdapter class
* */
interface DialogCallback {
    fun onPositiveClick(reason:String)
}


interface PopupMenuClick{
    fun onSelect(pos:Int,itemPos:Int)
}
interface TextWatcherCallback{
    fun onTextChanged(p0: CharSequence?)
}
interface OnTimePickCallback
{
    fun onTmePicked(hour:Int,min:Int)
}
interface AdapterItemClickCallback
{
    fun onItemSelect(pos:Int)
}

