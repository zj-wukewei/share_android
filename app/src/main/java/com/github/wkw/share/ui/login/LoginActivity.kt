package com.github.wkw.share.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import com.github.wkw.share.R
import com.github.wkw.share.UserManager
import com.github.wkw.share.base.BaseActivity
import com.github.wkw.share.databinding.ActivityLoginBinding
import com.github.wkw.share.ui.extens.navigateToActivity
import com.github.wkw.share.ui.main.MainActivity
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

class LoginActivity : BaseActivity<ActivityLoginBinding>(), View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var loginViewModel: LoginViewModel

    @Inject
    lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        setFullscreen(true)
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        if (userManager.token.isNotEmpty()) {
            navigateToActivity(MainActivity::class.java)
            finish()
        }
        loginViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(LoginViewModel::class.java)
        mBinding.run {
            vm = loginViewModel
            presenter = this@LoginActivity
            setLifecycleOwner(this@LoginActivity)
        }
        loginViewModel.loginSuccess.observe(this, Observer { it ->
            it.let {
                navigateToActivity(MainActivity::class.java)
                finish()
            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    private fun attemptSubmit() {
        loginViewModel.login()
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