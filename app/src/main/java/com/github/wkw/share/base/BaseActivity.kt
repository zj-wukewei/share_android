package com.github.wkw.share.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager

abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {

    lateinit var mBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        initView()
        fetchData()
    }

    abstract fun getLayoutId(): Int

    abstract fun fetchData()

    abstract fun initView()

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