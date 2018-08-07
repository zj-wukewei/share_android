package com.github.wkw.share.di

import com.github.wkw.share.ui.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun provideLoginActivity(): LoginActivity
}