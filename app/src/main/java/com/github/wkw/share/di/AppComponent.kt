package com.github.wkw.share.di

import android.app.Application
import com.github.wkw.share.ShareApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author GoGo on 2018/8/6.
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    AppModule::class, ActivityModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(shareApplication: ShareApplication)

}