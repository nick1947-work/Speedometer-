package com.creation.core_pulgin.core.base.extention

import android.content.Context
import android.widget.Toast


inline fun Context.showToast(text: String, length: Int = Toast.LENGTH_LONG) = Toast.makeText(this, text,
        length).show()



fun Context.appName(): String {
    val appInfo = this.applicationInfo
    val appNameRes = appInfo.labelRes
    return if (appNameRes == 0) appInfo.nonLocalizedLabel.toString() else getString(appNameRes)
}