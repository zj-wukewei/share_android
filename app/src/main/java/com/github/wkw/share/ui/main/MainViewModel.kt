package com.github.wkw.share.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.wkw.share.repository.UserRepository
import com.github.wkw.share.utils.ext.asLiveData
import com.github.wkw.share.vo.UserInfo
import javax.inject.Inject

class MainViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    fun userInfo(): LiveData<UserInfo> {
        return userRepository.userInfo()
                .asLiveData()
    }
}