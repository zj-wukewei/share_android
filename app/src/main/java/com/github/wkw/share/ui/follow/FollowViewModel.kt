package com.github.wkw.share.ui.follow

import android.arch.lifecycle.MutableLiveData
import com.github.wkw.share.repository.UserRepository
import com.github.wkw.share.utils.ext.subscribeBy
import com.github.wkw.share.viewmodel.AutoDisposeViewModel
import com.github.wkw.share.vo.Follow
import com.uber.autodispose.autoDisposable
import javax.inject.Inject

class FollowViewModel @Inject constructor(private val userRepository: UserRepository) : AutoDisposeViewModel() {

    val result = MutableLiveData<List<Follow>>()

    init {
        userRepository
                .syncRemoteFollows()
                .autoDisposable(this)
                .subscribeBy { userRepository.insertFollowsAll(it) }
    }

    fun myFollow() {
        userRepository.getLocalFollows()
                .autoDisposable(this)
                .subscribeBy(onNext = {
                    result.postValue(it)
                })
    }


    fun fans() {
        userRepository.myFans()
                .autoDisposable(this)
                .subscribeBy(onNext = {
                    result.postValue(it)
                })
    }

    fun follow(userId: String) = userRepository.follow(userId)
}