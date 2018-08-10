package com.github.wkw.share.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.github.wkw.share.ui.home.HomeViewModel
import com.github.wkw.share.ui.login.LoginViewModel
import com.github.wkw.share.viewmodel.ShareViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author GoGo on 2018/8/6.
 */
@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel
    @Binds
    abstract fun bindViewModelFactory(factory: ShareViewModelFactory): ViewModelProvider.Factory

}