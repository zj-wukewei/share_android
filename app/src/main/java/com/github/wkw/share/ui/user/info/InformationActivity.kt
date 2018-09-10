package com.github.wkw.share.ui.user.info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.wkw.share.R
import com.github.wkw.share.base.BaseActivity
import com.github.wkw.share.databinding.ActivityInformationBinding

class InformationActivity : BaseActivity<ActivityInformationBinding>() {

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, InformationActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBackToolbar(mBinding.toolbarLayout.toolbar, getString(R.string.settings_personal_info))
    }

    override fun getLayoutId(): Int = R.layout.activity_information
}