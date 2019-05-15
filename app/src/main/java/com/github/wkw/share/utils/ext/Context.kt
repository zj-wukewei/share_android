package com.github.wkw.share.utils.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import com.github.wkw.share.utils.ToastUtils

inline fun Context.toast(msg: String) = ToastUtils.showToast(this, msg)

inline fun Context.getCompatColor(colorId: Int) = ContextCompat.getColor(this, colorId)

inline fun Context.mySharedPreferences(): SharedPreferences = this.getSharedPreferences("Share", Context.MODE_PRIVATE)

inline fun Activity.navigateToActivity(clazz: Class<*>) = startActivity(Intent(this, clazz))


