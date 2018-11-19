package com.github.wkw.share;

import android.app.Activity
import android.app.Application
import com.github.wkw.share.api.exception.NetworkConnectionException
import com.github.wkw.share.api.exception.ResponseException
import com.github.wkw.share.di.DaggerAppComponent
import com.github.wkw.share.utils.ext.toast
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber
import java.net.ConnectException
import javax.inject.Inject


/**
 * @author GoGo on 2018/8/6.
 */

class ShareApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
        Timber.plant(Timber.DebugTree())
        initRxJavaError()
    }

    private fun initRxJavaError() {
        RxJavaPlugins.setErrorHandler { it ->
            Timber.d(it)
            when (it.cause) {
                is ConnectException -> toast(getString(R.string.network_error))
                is NetworkConnectionException -> toast(getString(R.string.network_error))
                is ResponseException -> it.message?.let { it1 -> toast(it1) }
                else -> toast(getString(R.string.server_error))
            }
        }
    }


    override fun activityInjector() = dispatchingAndroidInjector
}
