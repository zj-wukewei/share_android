package com.github.wkw.share.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.github.wkw.share.ui.extens.asLiveData
import com.github.wkw.share.repository.UserRepository
import com.github.wkw.share.vo.UserInfo
import javax.inject.Inject

class MainViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    fun userInfo(): LiveData<UserInfo> {
        return userRepository.userInfo()
                .asLiveData()
    }
}