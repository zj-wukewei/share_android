package com.github.wkw.share.ui.home

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.github.wkw.share.AppExecutors
import com.github.wkw.share.R
import com.github.wkw.share.base.BaseFragment
import com.github.wkw.share.base.adapter.ItemClickPresenter
import com.github.wkw.share.databinding.FragmentHomeBinding
import com.github.wkw.share.vo.Feed
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding>(), ItemClickPresenter<Feed> {


    companion object {
        fun newInstance() = HomeFragment()
    }

    private val mAdapter by lazy {
        HomeAdapter(appExecutors).apply {
            itemPresenter = this@HomeFragment
        }
    }

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    override fun onItemClick(v: View?, item: Feed) {
    }

    private fun initView() {
        mBinding.fragmentList.recyclerView.run {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

        var list = arrayListOf(Feed(1, "aaaaa", "ssssss"), Feed(121, "aaaaa", "ssssss"), Feed(22, "aaaaa", "ssssss"))
        mAdapter.submitList(list)

        mBinding.fragmentList.recyclerView.postDelayed({
            list.clear()
            mAdapter.submitList(arrayListOf())
        }, 3000)
    }
}