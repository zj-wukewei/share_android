package com.github.wkw.share.ui.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.github.wkw.share.UserManager
import com.github.wkw.share.api.reponse.UserEntity
import com.github.wkw.share.api.request.LoginRequest
import com.github.wkw.share.ui.extens.asResourceLiveData
import com.github.wkw.share.repository.UserRepository
import com.github.wkw.share.vo.Resource
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userRepository: UserRepository, private val userManager: UserManager) : ViewModel() {
    val loginRequest: LoginRequest = LoginRequest()
    fun login(): LiveData<Resource<UserEntity>> {
        return userRepository.login(loginRequest)
                .doOnNext { userManager.setUserEntity(it) }
                .asResourceLiveData()
    }
}