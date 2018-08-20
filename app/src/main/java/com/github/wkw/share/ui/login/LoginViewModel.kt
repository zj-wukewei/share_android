package com.github.wkw.share.ui.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.wkw.share.UserManager
import com.github.wkw.share.api.reponse.UserEntity
import com.github.wkw.share.api.request.LoginRequest
import com.github.wkw.share.repository.UserRepository
import com.github.wkw.share.utils.encode
import com.github.wkw.share.viewmodel.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userRepository: UserRepository, private val userManager: UserManager) : BaseViewModel() {
    val mobile = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()
    val loginSuccess = MutableLiveData<Boolean>()

    fun login() {
        userRepository.login(LoginRequest(mobile = mobile.value, password = encode(password.value)))
                .doOnSubscribe { isLoading.postValue(true) }
                .doFinally { isLoading.postValue(false) }
                .doOnNext { userManager.setUserEntity(it) }
                .subscribeBy(onNext = {
                    loginSuccess.postValue(true)
                })
                .addTo(disposable)
    }

}