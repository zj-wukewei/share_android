package com.github.wkw.share.api

import android.os.Build
import com.github.wkw.share.BuildConfig
import com.github.wkw.share.UserManager
import okhttp3.Interceptor
import okhttp3.Response


class HeadInterceptor(private val userManager: UserManager) : Interceptor {

    private val TAG = "HeadInterceptor"

    val TOKEN = "TOKEN"
    val APP_ID = "APP-ID"//1 ios 2 安卓
    val APP_VERSION = "APP-VERSION"//app版本
    val APP_MODEL = "APP-MODEL"//设备机型【如:iPhone 6/Meizu Pro 6/iPhone 7】
//    val DEVICE_SYSTEM = "DEVICE-SYSTEM"
//    val APP_VERSION_CODE = "APP_VERSION_CODE"
//    val NETWORK = "NETWORK"
//    val LANGUAGE = "Accept-Language"


    override fun intercept(chain: Interceptor.Chain?): Response? {
        val original = chain!!.request()
        val request = original.newBuilder()
                .method(original.method(), original.body())
                .addHeader(TOKEN, userManager.token)
                .addHeader(APP_ID, "2")
                .addHeader(APP_VERSION, BuildConfig.VERSION_CODE.toString())
                .addHeader(APP_MODEL, Build.MODEL)
//
                .build()
        return chain.proceed(request)

    }
}