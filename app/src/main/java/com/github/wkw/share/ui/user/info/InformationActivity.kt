package com.github.wkw.share.ui.user.info

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.github.wkw.share.R
import com.github.wkw.share.base.BaseActivity
import com.github.wkw.share.databinding.ActivityInformationBinding
import com.github.wkw.share.utils.ext.toast
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
        mBinding.toolbarLayout.toolbar
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

        informationViewModel.updateSuccess.observe(this, Observer { it ->
            it?.let {
                toast(getString(R.string.update_info_success))
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_infromation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_information_save -> informationViewModel.updateInfo()
        }
        return true
    }

    override fun getLayoutId(): Int = R.layout.activity_information

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_info_sex -> {
                val sex = resources.getStringArray(R.array.sex)
                MaterialDialog(this).show {
                    listItems(R.array.sex) { dialog, index, text ->
                        mBinding.userInfo?.apply {
                            gender = index + 1
                        }
                    }
                }
            }
            R.id.tv_info_pet -> {
                val item = mBinding.categoryList?.map { item -> item.name }
                MaterialDialog(this).show {
                    listItems(items = item) { dialog, index, text ->
                        mBinding.userInfo?.apply {
                            categoryId =  mBinding.categoryList?.get(index)?.id
                        }
                    }
                }
            }

        }
    }
}