package com.github.wkw.share.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

    protected val mScopeProvider: ScopeProvider by lazy { AndroidLifecycleScopeProvider.from(this) }

    protected lateinit var mBinding: VB
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), null, false)
        mBinding.setLifecycleOwner(this)
        return mBinding.root
    }

    abstract fun getLayoutId(): Int

}