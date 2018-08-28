package com.github.wkw.share.di

import android.app.Application
import android.content.Context
import com.github.wkw.share.BuildConfig
import com.github.wkw.share.UserManager
import com.github.wkw.share.api.HeadInterceptor
import com.github.wkw.share.api.ShareService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author GoGo on 2018/8/6.
 */
@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideOkHttp(userManager: UserManager): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addNetworkInterceptor(HeadInterceptor(userManager))
                .addInterceptor(loggingInterceptor)
                .connectTimeout(12, TimeUnit.SECONDS)
                .readTimeout(12, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun provideShareService(okHttpClient: OkHttpClient): ShareService {
        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create()
        return Retrofit.Builder()
                .baseUrl(BuildConfig.HOST_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ShareService::class.java)
    }

    @Singleton
    @Provides
    fun provideContext(app: Application): Context {
        return app
    }


    @Singleton
    @Provides
    fun provideUserManager(app: Application): UserManager {
        return UserManager(app.applicationContext)
    }


}