package com.github.wkw.share.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.view.WindowManager
import com.github.wkw.share.R
import com.github.wkw.share.utils.ext.getCompatColor
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {

    protected val mScopeProvider: ScopeProvider by lazy { AndroidLifecycleScopeProvider.from(this) }

    lateinit var mBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
    }

    abstract fun getLayoutId(): Int

    fun initBackToolbar(toolbar: Toolbar, title: String? = null) {
        setSupportActionBar(toolbar)
        val bar = supportActionBar
        if (bar != null) {
            title?.let {
                bar.title = title
            }
            val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
            upArrow?.setColorFilter(getCompatColor(R.color.black), PorterDuff.Mode.SRC_ATOP)
            bar.setHomeAsUpIndicator(upArrow)
            bar.setDisplayHomeAsUpEnabled(true)
            bar.setDisplayShowHomeEnabled(true)
            bar.setDisplayShowTitleEnabled(true)
            bar.setHomeButtonEnabled(true)
        }
        toolbar.setTitleTextColor(getCompatColor(R.color.black))

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun setFullscreen(fullscreen: Boolean) {
        val window = window
        val winParams = window.attributes
        val bits = WindowManager.LayoutParams.FLAG_FULLSCREEN
        if (fullscreen) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        window.attributes = winParams
    }
}