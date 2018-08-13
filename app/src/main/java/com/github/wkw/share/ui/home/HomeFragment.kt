package com.github.wkw.share.ui.home

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.github.wkw.share.AppExecutors
import com.github.wkw.share.R
import com.github.wkw.share.base.BaseFragment
import com.github.wkw.share.base.adapter.ItemClickPresenter
import com.github.wkw.share.databinding.FragmentHomeBinding
import com.github.wkw.share.utils.Live
import com.github.wkw.share.vo.Feed
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import dagger.android.support.AndroidSupportInjection
import io.reactivex.rxkotlin.subscribeBy
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
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(HomeViewModel::class.java)
        homeViewModel.feeds("")
                .compose(Live.bindLifecycle(this))
                .subscribeBy(onNext = {
                    mAdapter.submitList(it.list)
                })
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
            addItemDecoration(
                    HorizontalDividerItemDecoration.Builder(context)
                            .colorResId(R.color.home_item_divider_color)
                            .sizeResId(R.dimen.home_divider_height)
                            .build()
            )
        }
    }
}