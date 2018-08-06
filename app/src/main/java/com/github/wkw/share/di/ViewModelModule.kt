package com.github.wkw.share.di

import android.arch.lifecycle.ViewModelProvider
import com.github.wkw.share.viewmodel.ShareViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * @author GoGo on 2018/8/6.
 */
@Suppress("unused")
@Module
abstract class ViewModelModule {
//    @Binds
//    @IntoMap
//    @ViewModelKey(RepoViewModel::class)
//    abstract fun bindRepoViewModel(repoViewModel: RepoViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ShareViewModelFactory): ViewModelProvider.Factory

}