package com.github.wkw.share.di

import com.github.wkw.share.ui.follow.FollowActivity
import com.github.wkw.share.ui.login.LoginActivity
import com.github.wkw.share.ui.main.MainActivity
import com.github.wkw.share.ui.user.info.InformationActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun provideLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [FragmentMainModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun provideFollowActivity(): FollowActivity

    @ContributesAndroidInjector
    abstract fun provideInformationActivity(): InformationActivity

}