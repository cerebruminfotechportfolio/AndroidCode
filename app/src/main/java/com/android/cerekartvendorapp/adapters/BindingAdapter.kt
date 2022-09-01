package com.android.cerekartvendorapp.adapters

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.utils.FontStyle
import com.android.cerekartvendorapp.utils.UtilsFunctions
import com.bumptech.glide.Glide

import java.text.SimpleDateFormat
import java.util.*

object BindingAdapter {

    @BindingAdapter("android:src")
    @JvmStatic
    fun setImageViewResource(imageView: ImageView, imgUrl: Drawable) {
        Glide.with(imageView.context).load(imgUrl)
            .placeholder(R.color.black)
            .into(imageView)
    }


    @BindingAdapter("hideKeyBoardOnClick")
    @JvmStatic
    fun bindOnHideKeyboardOnClick(view: View, text: String) {
        view.setOnClickListener { view ->
            UtilsFunctions.hideKeyBoard(view)
        }
    }

    @BindingAdapter("onBackClick")
    @JvmStatic
    fun bindOnBackClick(view: View, text: String) {
        view.setOnClickListener { view ->
            val activity = getActivity(view)
            activity!!.finish()
            UtilsFunctions.hideKeyBoard(view)
        }
    }


    private fun getActivity(v: View): Activity? {
        var context = v.context
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = (context).baseContext
        }
        return null
    }


    @BindingAdapter("recyclerViewTouch")
    @JvmStatic
    fun recyclerViewTouch(rec:RecyclerView,temp:String)
    {
        rec.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                UtilsFunctions.hideKeyBoard(rec)
                return false
            }

        })
    }

    @BindingAdapter("changePasswordToggle")
    @JvmStatic
    fun changePasswordToggle(imageView: AppCompatImageView, temp: Int?) {
        imageView.setOnClickListener {
            val toggleValue = (imageView.tag as? Boolean) ?: false
            imageView.parent?.let { viewParent ->
                val viewGroup = viewParent as ViewGroup
                if (viewGroup.childCount > 0 && viewGroup.getChildAt(0) is AppCompatEditText) {
                        val editText = viewGroup.getChildAt(0) as AppCompatEditText
                        if (toggleValue) {
                            editText.transformationMethod = PasswordTransformationMethod()
                            imageView.setImageResource(R.drawable.ic_closed_eye)
                        } else {
                            editText.transformationMethod = null
                             imageView.setImageResource(R.drawable.ic_open_eye)
                        }
                        editText.setSelection(editText.text.toString().length)
                        imageView.tag = !toggleValue
                    }
            }
        }
    }
    @BindingAdapter("datePicker")
    @JvmStatic
    fun datePicker(tvDate: TextView, format: String) {
        tvDate.setOnClickListener {
            val datePickerDialog =
                DatePickerDialog(
                    getActivity(tvDate)!!,
                    DatePickerDialog.OnDateSetListener { _, year, month, day ->
                        val cal = Calendar.getInstance()
                        cal.set(Calendar.MONTH,month)
                        cal.set(Calendar.DAY_OF_MONTH,day)
                        cal.set(Calendar.YEAR,year)
                        tvDate.text = SimpleDateFormat(format,Locale.getDefault()).format(cal.time)
                    },
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                )
           // datePickerDialog.datePicker.minDate = Calendar.getInstance().timeInMillis
            datePickerDialog.show()
        }

    }


    @BindingAdapter("bind:font")
    @JvmStatic
    fun setFont(textView: TextView, fontName: String) {
        textView.typeface = FontStyle.instance.getFont(fontName)
    }
}
