package com.github.wkw.share.ui.home

import com.github.wkw.share.R
import com.github.wkw.share.base.BaseFragment
import com.github.wkw.share.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }
}