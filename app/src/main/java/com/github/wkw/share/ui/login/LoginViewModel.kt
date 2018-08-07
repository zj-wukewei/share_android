package com.github.wkw.share.ui.login

import android.arch.lifecycle.ViewModel
import com.github.wkw.share.api.reponse.UserEntity
import com.github.wkw.share.api.request.LoginRequest
import com.github.wkw.share.repository.UserRepository
import io.reactivex.Observable
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    val loginRequest: LoginRequest = LoginRequest()

    fun login(): Observable<UserEntity> {
        return userRepository.login(loginRequest)
    }
}