package com.github.wkw.share.repository

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.github.wkw.share.R
import com.github.wkw.share.vo.LikeEvent
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NotificationFactory @Inject constructor(private val context: Context) {

    companion object {
        val LIKECHANNELID = "LIKE"
    }

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    private fun createLikeNotification(likeEvent: LikeEvent): Notification {
        Timber.d("createLikeNotification")
        val notificationBuilder = NotificationCompat.Builder(context, "com.share")
        val builder = notificationBuilder.setAutoCancel(true)
                .setContentTitle("${likeEvent.nickName}喜欢了你的分享")
                .setContentText(likeEvent.content)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

        return builder.build()
    }

    fun showLikeNotification(likeEvent: LikeEvent?) {
        likeEvent?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                        "com.share",
                        "like",
                        NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }
            notificationManager.notify(1, createLikeNotification(likeEvent))
        }
    }
}