package com.github.wkw.share.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.github.wkw.share.R
import com.github.wkw.share.base.BaseActivity
import com.github.wkw.share.databinding.ActivitySettingsBinding
import com.github.wkw.share.ui.user.info.InformationActivity

class SettingsActivity : BaseActivity<ActivitySettingsBinding>(), View.OnClickListener {


    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, SettingsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.presenter = this
        initBackToolbar(mBinding.toolbarLayout.toolbar, getString(R.string.setting))
    }

    override fun getLayoutId(): Int = R.layout.activity_settings

    override fun onClick(v: View?) {
        InformationActivity.startActivity(this@SettingsActivity)
    }
}