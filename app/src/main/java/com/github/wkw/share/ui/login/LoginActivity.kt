package com.github.wkw.share.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.github.wkw.share.ui.main.MainActivity
import com.github.wkw.share.R
import com.github.wkw.share.UserManager
import com.github.wkw.share.base.BaseActivity
import com.github.wkw.share.databinding.ActivityLoginBinding
import com.github.wkw.share.ui.extens.navigateToActivity
import com.github.wkw.share.ui.extens.observeBy
import com.github.wkw.share.vo.Status
import dagger.android.AndroidInjection
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
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    private fun attemptSubmit() {
        loginViewModel.login()
                .observeBy(this,
                        onLoading = {
                            mBinding.isLoading = it
                        },
                        onSuccess = {
                            navigateToActivity(MainActivity::class.java)
                            finish()
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