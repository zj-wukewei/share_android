package com.github.wkw.share.ui.login

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import com.github.wkw.share.UserManager
import com.github.wkw.share.api.request.LoginRequest
import com.github.wkw.share.repository.UserRepository
import com.github.wkw.share.utils.encode
import com.github.wkw.share.utils.ext.subscribeBy
import com.github.wkw.share.viewmodel.AutoDisposeViewModel
import com.uber.autodispose.autoDisposable
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userRepository: UserRepository, private val userManager: UserManager) : AutoDisposeViewModel() {
    val mobile = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val loginSuccess = MutableLiveData<Boolean>()

    @SuppressLint("CheckResult")
    fun login() {
        userRepository.login(LoginRequest(mobile = mobile.value, password = encode(password.value)))
                .doOnSubscribe { loading.postValue(true) }
                .doFinally { loading.postValue(false) }
                .doOnNext {
                    userManager.setUserEntity(it)
                }
                .autoDisposable(this)
                .subscribeBy(onNext = {
                    loginSuccess.postValue(true)
                })
    }

}