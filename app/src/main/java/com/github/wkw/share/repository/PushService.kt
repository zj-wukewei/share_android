package com.github.wkw.share.repository

import android.content.Context
import android.os.Build
import com.github.wkw.share.BuildConfig
import com.github.wkw.share.UserManager
import com.github.wkw.share.api.HeadInterceptor
import com.github.wkw.share.vo.PushEvent
import com.google.gson.Gson
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import okhttp3.OkHttpClient
import timber.log.Timber
import ua.naiksoftware.stomp.LifecycleEvent
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.client.StompClient
import ua.naiksoftware.stomp.client.StompMessage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PushService @Inject constructor(okHttpClient: OkHttpClient, private val userManager: UserManager, context: Context) {

    private var mStompClient: StompClient
    private val gson = Gson()

    init {

        val headerMap = HashMap<String, String>()
        headerMap[HeadInterceptor.TOKEN] = userManager.token
        headerMap[HeadInterceptor.APP_ID] = HeadInterceptor.ANDROID_APP_ID
        headerMap[HeadInterceptor.APP_VERSION] = BuildConfig.VERSION_CODE.toString()
        headerMap[HeadInterceptor.APP_MODEL] = Build.MODEL
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://192.168.8.141:8080/share/websocket", headerMap, okHttpClient)
        mStompClient.lifecycle().subscribe { it ->
            when (it?.type) {
                LifecycleEvent.Type.OPENED -> Timber.d("OPENED")
                LifecycleEvent.Type.ERROR -> Timber.e(it.exception)
                LifecycleEvent.Type.CLOSED -> Timber.d("CLOSED")
            }
        }
        if (!mStompClient.isConnected) {
            mStompClient.connect()
        }

        mStompClient.topic("/user/${userManager.uId}/msg")
                .map { it.payload }
                .map { gson.fromJson(it, PushEvent::class.java) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { it ->
                    it?.let {
                        when (it.type) {
                            1 -> {
                                Timber.d("接收到点赞的推送 %s", it.data.toString())
                            }
                            2 -> {

                            }
                        }
                    }
                }
    }

    fun connect() {
        if (!mStompClient.isConnected) {
            mStompClient.connect()
        }
    }

    fun disconnect() {
        mStompClient.disconnect()
    }

    fun followEvent(): Flowable<StompMessage> {
        return mStompClient.topic("/user/${userManager.uId}/msg")
                .observeOn(AndroidSchedulers.mainThread())
    }
}