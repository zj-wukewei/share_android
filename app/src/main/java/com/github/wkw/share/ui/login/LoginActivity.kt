package com.github.wkw.share.ui.login

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.View
import com.github.wkw.share.AppExecutors
import com.github.wkw.share.R
import com.github.wkw.share.api.request.LoginRequest
import com.github.wkw.share.base.BaseActivity
import com.github.wkw.share.databinding.ActivityLoginBinding
import com.github.wkw.share.utils.Live
import dagger.android.AndroidInjection
import io.reactivex.functions.Consumer
import javax.inject.Inject

class LoginActivity : BaseActivity<ActivityLoginBinding>(), View.OnClickListener {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setFullscreen(true)
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(LoginViewModel::class.java)
        mBinding.vm = loginViewModel
        mBinding.presenter = this@LoginActivity
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun fetchData() {
    }

    override fun initView() {
    }

    private fun attemptSubmit() {
        loginViewModel.login()
                .compose(Live.bindLifecycle(this@LoginActivity))
                .subscribe({
                }, {
                })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> attemptSubmit()

            R.id.tv_login_forget_password -> {

            }

            R.id.tv_login_register -> {

            }
        }
    }
}