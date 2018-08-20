package com.github.wkw.share.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.github.wkw.share.AppExecutors
import com.github.wkw.share.R
import com.github.wkw.share.base.PageLazyFragment
import com.github.wkw.share.base.adapter.ItemClickPresenter
import com.github.wkw.share.ui.extens.navigateToActivity
import com.github.wkw.share.ui.login.LoginActivity
import com.github.wkw.share.vo.Feed
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import javax.inject.Inject

class HomeFragment : PageLazyFragment(), ItemClickPresenter<Feed> {


    companion object {
        const val HOME: String = "2"
        const val HOT: String = "3"
        const val COMMUNITY: String = "1"
        private const val TYPE: String = "TYPE"
        fun newInstance(type: String): HomeFragment {
            val homeFragment = HomeFragment()
            val bundle = Bundle()
            bundle.putString(TYPE, type)
            homeFragment.arguments = bundle
            return homeFragment
        }
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

    override fun loadData() {
        homeViewModel.onSwipeRefresh()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(HomeViewModel::class.java)
        val type: String? = arguments?.getString(TYPE)
        homeViewModel.feedQry.type = type
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        homeViewModel.responseResult.observe(this, Observer {
            Timber.d(it.toString())
            mAdapter.submitList(it)
        })
    }


    override fun onItemClick(v: View?, item: Feed) {
    }


    override fun onPause() {
        super.onPause()
        Timber.d("onPause")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
    }

    private fun initView() {
        mBinding.run {
            vm = homeViewModel
            setLifecycleOwner(this@HomeFragment)
        }
        mBinding.recyclerView.apply {
            adapter = mAdapter
            addItemDecoration(
                    HorizontalDividerItemDecoration.Builder(context)
                            .colorResId(R.color.home_item_divider_color)
                            .sizeResId(R.dimen.home_divider_height)
                            .build()
            )
            (itemAnimator as DefaultItemAnimator).run {
                supportsChangeAnimations = false
                addDuration = 160L
                moveDuration = 160L
                changeDuration = 160L
                removeDuration = 120L
            }
            layoutManager = LinearLayoutManager(context)
        }
    }
}