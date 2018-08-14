package com.github.wkw.share.base

import android.os.Bundle
import com.github.wkw.share.R
import com.github.wkw.share.databinding.FragmentListBinding

abstract class PageLazyFragment : BaseFragment<FragmentListBinding>() {


    private var isViewInitiated: Boolean = false
    private var isVisibleToUser: Boolean = false
    private var isDataInitiated: Boolean = false


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewInitiated = true
        prepareFetchData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        prepareFetchData()
    }


    private fun prepareFetchData(): Boolean {
        return prepareFetchData(false)
    }

    /**
     * 准备去取数据
     *
     * @param forceUpdate 是否强制刷新数据
     * @return
     */
    private fun prepareFetchData(forceUpdate: Boolean): Boolean {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            loadData()
            isDataInitiated = true
            return true
        }
        return false
    }

    abstract fun loadData()

    override fun getLayoutId() = R.layout.fragment_list
}