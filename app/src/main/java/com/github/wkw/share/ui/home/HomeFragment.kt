package com.github.wkw.share.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.github.wkw.share.BR
import com.github.wkw.share.R
import com.github.wkw.share.base.PageLazyFragment
import com.github.wkw.share.base.adapter.ItemClickPresenter
import com.github.wkw.share.base.adapter.MagicPageAdapter
import com.github.wkw.share.utils.ext.subscribeBy
import com.github.wkw.share.vo.Feed
import com.uber.autodispose.autoDisposable
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
        MagicPageAdapter
                .Builder(R.layout.item_home, diffCallback = object : DiffUtil.ItemCallback<Feed>() {
                    override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean =
                            oldItem.id == newItem.id

                    override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean =
                            oldItem.liked == newItem.liked && oldItem.content == newItem.content && oldItem.commentCount == newItem.commentCount
                })
                .handler(BR.presenter, this@HomeFragment)
                .handler(BR.onItemClick, object : ItemClickPresenter<Feed> {
                    override fun onItemClick(item: Feed) {
                        context?.let { HomeDetailActivity.startActivity(context!!, item.id) }
                    }
                })
                .build()
    }


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var homeViewModel: HomeViewModel


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun loadData() {
        homeViewModel.initDataRepository()
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
        homeViewModel.results.observe(this, Observer { it ->
            Timber.d("aaaaaaaaaaaaaaaaaaaa")
            mAdapter.submitList(it)
        })
    }

    override fun onItemClick(item: Feed) {
        homeViewModel.like(item.id)
                .autoDisposable(mScopeProvider)
                .subscribeBy(
                        onNext = { it ->
                            item.likeCount = it.lickCount
                            item.liked = it.liked
                        }
                )
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
            layoutManager = LinearLayoutManager(context)
        }
    }
}