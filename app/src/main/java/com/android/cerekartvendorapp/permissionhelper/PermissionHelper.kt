package com.android.cerekartvendorapp.permissionhelper

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.cerekartvendorapp.CereKartVendorApp
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.constants.PermissionConstants


const val PERMISION_GRANTED = 1
const val PERMISION_REVOKED = 2
const val PERMISION_RATIONAL = 3

class PermissionHelper {
    /**
     * A [IGetPermissionListener] object to sends call back to the Activity which implements it
     */
    private lateinit var mGetPermissionListener: IGetPermissionListener

    fun setListener(mGetPermissionListener: IGetPermissionListener) {
        this.mGetPermissionListener = mGetPermissionListener
    }

    /**
     * A [Activity] object to get the context of the Activity from where it is called
     */
    private var mActivity: Activity? = null
    private var fragment: Fragment? = null

    /**
     * Method to check any permission. It will return true if permission granted
     * otherwise false
     *
     * @param requestCode is the code given for which permission is to be taken
     * @param context     of the activity
     * @param permissions that we want to take from the user
     * @return true if permission granted otherwise false
     */
    fun hasPermission(
        context: Activity,
        permissions: Array<String>,
        requestCode: Int): Boolean {
        mActivity = context
        var isAllGranted = true
        //check for all devices
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
            ) {
                isAllGranted = false
            }
        }
        //check if all granted or not
        return if (!isAllGranted) {
            ActivityCompat.requestPermissions(context, permissions, requestCode)
            false
        }
        else true
    }

    /**
     * Method to check any permission. It will return true if permission granted
     * otherwise false
     *
     * @param mActivity
     * @param fragment    of the activity
     * @param permissions that we want to take from the user
     * @param requestCode is the code given for which permission is to be taken
     * @return true if permission granted otherwise false
     */
    fun hasPermissionInFragment(
        mActivity: Activity?,
        fragment: Fragment,
        permissions: Array<String>,
        requestCode: Int
    ): Boolean {
        this.mActivity = mActivity
        this.fragment = fragment
        var isAllGranted = true
        //check for all devices
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    fragment.requireContext()
                    , permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                isAllGranted = false
            }
        }
        //check if all granted or not
        return if (!isAllGranted) {
            fragment.requestPermissions(permissions, requestCode)
            false
        } else true
    }

    /**
     * Method to check any permission. It will return true if permission granted
     * otherwise false
     *
     * @param context     of the activity
     * @param permissions that we want to take from the user
     * @return true if permission granted otherwise false
     */
    fun checkPermissionGrantOrNot(
        context: Context?,
        permissions: Array<String>
    ): Boolean {
        var isAllGranted = true
        //check for all devices
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    context!!,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                isAllGranted = false
                break
            }
        }
        //check if all granted or not
        return isAllGranted
    }

    /**
     * This method is used to set the result when
     * gets the callback of the permission
     *
     * @param requestCode  code at which particular permission was asked
     * @param permissions  name of the permission taken from the user
     * @param grantResults gives the result whether user grants or denies the permission
     */
    fun setPermissionResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray?
    ) {
        var isAllPermissionGranted = PERMISION_GRANTED
        for (i in permissions.indices) {


            if (grantResults!![i] == PackageManager.PERMISSION_GRANTED) {
                isAllPermissionGranted = PERMISION_GRANTED
            }

            else if (ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity!!,
                    permissions[i]
                )
            ) {
                isAllPermissionGranted = PERMISION_REVOKED
                break
            } else {
                isAllPermissionGranted = PERMISION_RATIONAL
                break
            }
        }

        when (isAllPermissionGranted) {
            PERMISION_GRANTED -> {
                mGetPermissionListener.permissionGiven(requestCode)
            }
            PERMISION_REVOKED -> {
                mGetPermissionListener.permissionCancel(requestCode)
            }
            PERMISION_RATIONAL -> {
                showDialog(
                   CereKartVendorApp.instance.resources?.getString(R.string.permission_from_device)!!,
                    requestCode
                )
            }
        }
    }


    /*
     * method is used to show the setting go dialog
     * */
    private fun showDialog(message: String, requestCode: Int) {
        val builder = AlertDialog.Builder(mActivity)
        builder.setMessage(message)
            .setCancelable(false)
            .setNegativeButton(
                mActivity!!.resources.getString(R.string.cancel)
            ) { dialog, _ ->
                dialog.dismiss()
                mGetPermissionListener.permissionCancel(requestCode)
            }
            .setPositiveButton(
                mActivity!!.resources.getString(R.string.ok)
            ) { dialog, _ ->
                if (fragment != null) {
                    fragment!!.startActivityForResult(
                        Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + CereKartVendorApp.instance.packageName)
                        ), PermissionConstants.REQUEST_CODE_IN_SETTING
                    )
                } else {
                    mActivity!!.startActivityForResult(
                        Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + CereKartVendorApp.instance.packageName)
                        ), PermissionConstants.REQUEST_CODE_IN_SETTING
                    )
                }
            }
        val alert = builder.create()
        alert.show()
    }


    /**
     * This interface is used to get the user permission callback to the mActivity or fragment who
     * implements it
     */
    interface IGetPermissionListener {
        fun permissionGiven(requestCode: Int)
        fun permissionCancel(requestCode: Int)
    }

}