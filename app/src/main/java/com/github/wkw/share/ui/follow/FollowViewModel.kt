package com.github.wkw.share.ui.follow

import android.arch.lifecycle.MutableLiveData
import com.github.wkw.share.repository.UserRepository
import com.github.wkw.share.viewmodel.BaseViewModel
import com.github.wkw.share.vo.Follow
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class FollowViewModel @Inject constructor(private val userRepository: UserRepository): BaseViewModel() {

    val result = MutableLiveData<List<Follow>>()

    fun myFollow() {
        userRepository.myFollows()
                .subscribeBy(onNext = {
                    result.postValue(it)
                })
    }


    fun fans() {
        userRepository.myFans()
                .subscribeBy(onNext = {
                    result.postValue(it)
                })
    }

    fun follow(userId: String) = userRepository.follow(userId)
}