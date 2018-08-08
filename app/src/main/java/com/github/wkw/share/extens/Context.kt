package com.github.wkw.share.extens

import android.content.Context
import android.support.v4.content.ContextCompat
import android.widget.Toast

inline fun Context.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

inline fun Context.getCompatColor(colorId: Int) = ContextCompat.getColor(this, colorId)
