package com.github.wkw.share.extens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v4.content.ContextCompat
import android.widget.Toast

inline fun Context.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

inline fun Context.getCompatColor(colorId: Int) = ContextCompat.getColor(this, colorId)

inline fun Context.mySharedPreferences(): SharedPreferences = this.getSharedPreferences("Share", Context.MODE_PRIVATE)

inline fun Activity.navigateToActivity(clazz: Class<*>) = startActivity(Intent(this, clazz))


