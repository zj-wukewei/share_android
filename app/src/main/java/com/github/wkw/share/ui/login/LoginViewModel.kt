package com.github.wkw.share.ui.login

import android.arch.lifecycle.ViewModel
import com.github.wkw.share.UserManager
import com.github.wkw.share.api.reponse.UserEntity
import com.github.wkw.share.api.request.LoginRequest
import com.github.wkw.share.repository.UserRepository
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userRepository: UserRepository, private val userManager: UserManager) : ViewModel() {
    val loginRequest: LoginRequest = LoginRequest()
    val isLoading: PublishSubject<Boolean> = PublishSubject.create()

    fun login(): Observable<UserEntity> {
        return userRepository.login(loginRequest)
                .doOnNext { userManager.setUserEntity(it) }
                .doOnSubscribe { isLoading.onNext(true) }
                .doFinally { isLoading.onNext(false) }
    }
}