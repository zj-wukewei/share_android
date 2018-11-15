package com.github.wkw.share.ui.user.info

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.github.wkw.share.R
import com.github.wkw.share.base.BaseActivity
import com.github.wkw.share.databinding.ActivityInformationBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class InformationActivity : BaseActivity<ActivityInformationBinding>(), View.OnClickListener {


    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, InformationActivity::class.java)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var informationViewModel: InformationViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initBackToolbar(mBinding.toolbarLayout.toolbar, getString(R.string.settings_personal_info))
        mBinding.run {
            presenter = this@InformationActivity
            setLifecycleOwner(this@InformationActivity)
        }
        informationViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(InformationViewModel::class.java)

        informationViewModel.information.observe(this, Observer { it ->
            it?.let {
                mBinding.userInfo = it.userInfo
                mBinding.categoryList = it.categoryList
            }
        })

    }

    override fun getLayoutId(): Int = R.layout.activity_information

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_info_sex -> {
                MaterialDialog(this).show {
                    listItems(R.array.sex) { dialog, index, text ->
                    }
                }
            }
            R.id.tv_info_pet -> {
                val item = mBinding.categoryList?.map { item -> item.name }
                MaterialDialog(this).show {
                    listItems(items = item) { dialog, index, text ->
                    }
                }
            }

        }
    }
}