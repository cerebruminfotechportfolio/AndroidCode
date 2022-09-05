package com.android.cerekartvendorapp.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.text.format.DateUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.TimePicker
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import com.android.cerekartvendorapp.CereKartVendorApp
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.callbacks.OnTimePickCallback
import com.android.cerekartvendorapp.callbacks.PopupMenuClick
import com.android.cerekartvendorapp.constants.DateConstants
import com.android.cerekartvendorapp.constants.PermissionConstants
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object UtilsFunctions {

    private var mCapturedImagePath: String? = null

    @JvmStatic
    fun hideKeyBoard(view: View) {
        val imm = CereKartVendorApp.instance
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @JvmStatic
    fun onGalleryJustImageChoose(context: Activity) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        context.startActivityForResult(
            Intent.createChooser(
                intent,
                context.resources.getString(R.string.image_action)
            ), PermissionConstants.REQ_GALLERY
        )
    }

    @JvmStatic
    fun captureFile(context: Activity): Uri? {
        val myDir = File(context.externalCacheDir.toString() + "/CaptureImageDemo")
        if (!myDir.exists()) {
            myDir.mkdir()
        }
        val fName = "Image_Tracket-" + System.currentTimeMillis() + ".jpg"
        val photoFile = File(myDir, fName)
        mCapturedImagePath = photoFile.absolutePath
        return getValidUri(photoFile, context)
    }

    @JvmStatic
    fun getCapturedImagePath(): String? {
        return mCapturedImagePath
    }

    @JvmStatic
    fun openCamera(context: Activity, captureUri: Uri) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, captureUri)
        context.startActivityForResult(takePictureIntent, PermissionConstants.REQ_CAMERA)
    }

    @JvmStatic
    private fun getValidUri(file: File, context: Context): Uri? {
        var outputUri: Uri? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            outputUri =
                FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)
        }
        return outputUri
    }

    @SuppressLint("HardwareIds")
    fun getUniqueDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }


    @JvmStatic
    fun changeOutputFormat(inputFormat: String, outputFormat: String, date: String): String {
        val input = SimpleDateFormat(inputFormat, Locale.getDefault())
        val output = SimpleDateFormat(outputFormat, Locale.getDefault())
        try {
            val initialDate = input.parse(date)
            initialDate?.let {
                return output.format(it)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    @JvmStatic
    fun getTimeMills(inputFormat: String, date: String): Long? {
        val input = SimpleDateFormat(inputFormat, Locale.getDefault())
        try {
            val initialDate = input.parse(date)
            initialDate?.let {
                return initialDate.time
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }

    @JvmStatic
    fun showMenu(view: View, pos: Int, callback: PopupMenuClick) {
        val viewGroup = view.findViewById<ConstraintLayout>(R.id.main_view)
        val layoutInflater =
            (view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        val layout: View =
            layoutInflater.inflate(R.layout.popup_product_catalouge, viewGroup)
        val popup = PopupWindow(view.context)
        popup.contentView = layout
        popup.setBackgroundDrawable(ColorDrawable())
        popup.width = LinearLayout.LayoutParams.WRAP_CONTENT
        popup.height = LinearLayout.LayoutParams.WRAP_CONTENT
        popup.isOutsideTouchable = true
        popup.isFocusable = true

        val tvOptionOne = layout.findViewById<TextView>(R.id.tv_sub_cat)
        val tvOptionTwo = layout.findViewById<TextView>(R.id.tv_add_tax)
        var popPos = -1
        tvOptionOne.setOnClickListener {
            popup.dismiss()
            popPos = 2
            callback.onSelect(popPos, pos)

        }
        tvOptionTwo.setOnClickListener {
            popup.dismiss()
            popPos = 3
            callback.onSelect(popPos, pos)

        }
        popup.setTouchInterceptor { v: View?, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                callback.onSelect(popPos, pos)
                // popup.dismiss()
                // return@setTouchInterceptor true
            }
            false
        }
        popup.animationStyle = R.style.popupWindowAnim
        popup.showAsDropDown(view, -200, -40)

    }

    @JvmStatic
    fun showSubCatMenu(
        view: View,
        pos: Int,
        fromProduct: Boolean = false,
        callback: PopupMenuClick
    ) {
        val viewGroup = view.findViewById<ConstraintLayout>(R.id.main_view)
        val layoutInflater =
            (view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        val layout: View =
            layoutInflater.inflate(R.layout.popup_sub_category_option, viewGroup)
        val popup = PopupWindow(view.context)
        popup.contentView = layout
        popup.setBackgroundDrawable(ColorDrawable())
        popup.width = LinearLayout.LayoutParams.WRAP_CONTENT
        popup.height = LinearLayout.LayoutParams.WRAP_CONTENT
        popup.isOutsideTouchable = true
        popup.isFocusable = true

        val tvOptionOne = layout.findViewById<TextView>(R.id.tv_add_product)
        val tvOptionTwo = layout.findViewById<TextView>(R.id.tv_edit)
        val tvOptionThree = layout.findViewById<TextView>(R.id.tv_disable)
        val tvOptionfour = layout.findViewById<TextView>(R.id.tv_delete)
        var popPos = -1
        if (fromProduct) {
          tvOptionOne.visibility=View.GONE
        }
        tvOptionOne.setOnClickListener {
            popup.dismiss()
            popPos = 0
            callback.onSelect(popPos, pos)

        }
        tvOptionTwo.setOnClickListener {
            popup.dismiss()
            popPos = 1
            callback.onSelect(popPos, pos)

        }
        tvOptionThree.setOnClickListener {
            popup.dismiss()
            popPos = 2
            callback.onSelect(popPos, pos)

        }
        tvOptionfour.setOnClickListener {
            popup.dismiss()
            popPos = 3
            callback.onSelect(popPos, pos)

        }
        popup.setTouchInterceptor { v: View?, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                callback.onSelect(popPos, pos)
                // popup.dismiss()
                // return@setTouchInterceptor true
            }
            false
        }
        popup.animationStyle = R.style.popupWindowAnim
        popup.showAsDropDown(view, -200, -40)

    }

    @JvmStatic
    fun getFormattedDate(milisec: Long, format: String): String {
        if (DateUtils.isToday(milisec))
            return "Today, " + getStringDate(milisec, DateConstants.HH_MM_AA)
        return getStringDate(milisec, format)
    }

    fun getStringDate(milisec: Long, format: String): String {
        val inputFormat = SimpleDateFormat(format, Locale.getDefault())
        return inputFormat.format(milisec)
    }


    @JvmStatic
    fun enableDisableNoDataFound(count: Int, view: View) {
        if (count > 0)
            view.visibility = View.GONE
        else
            view.visibility = View.VISIBLE

    }

    @JvmStatic
    fun shareLink(activity: Activity, link: String?) {
        link?.let {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, it)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            activity.startActivity(shareIntent)
        }
    }

    @JvmStatic
    fun timePicker(context: Context, callback: OnTimePickCallback) {
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        mcurrentTime.add(Calendar.MINUTE, 10)
        val minute = mcurrentTime.get(Calendar.MINUTE)
        TimePickerDialog(context, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                callback.onTmePicked(hourOfDay, minute)
            }
        }, hour, minute, false).show()


    }
}

