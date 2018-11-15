package com.github.wkw.share.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.github.wkw.share.ui.follow.FollowViewModel
import com.github.wkw.share.ui.home.HomeViewModel
import com.github.wkw.share.ui.login.LoginViewModel
import com.github.wkw.share.ui.main.MainViewModel
import com.github.wkw.share.ui.user.info.InformationViewModel
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
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(FollowViewModel::class)
    abstract fun bindFollowVideModel(followVideModel: FollowViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InformationViewModel::class)
    abstract fun bindInformationViewModel(informationViewModel: InformationViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ShareViewModelFactory): ViewModelProvider.Factory

}