package com.github.wkw.share.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.github.wkw.share.ShareApplication

/**
 * Created by GoGo on 2018/11/19.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */
object ToastUtils {

    private val sMainThread = Looper.getMainLooper().thread
    private val sMainHandler = Handler(Looper.getMainLooper())


    fun showToast(context: Context, msg: String) {
        if (Thread.currentThread() == sMainThread) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        } else {
            sMainHandler.post { Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
        }
    }

    fun showToast(msg: String) {
        showToast(ShareApplication.INSTANCE, msg)
    }
}