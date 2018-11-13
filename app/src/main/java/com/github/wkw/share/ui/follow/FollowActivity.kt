package com.github.wkw.share.ui.follow

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.github.wkw.share.AppExecutors
import com.github.wkw.share.R
import com.github.wkw.share.base.BaseActivity
import com.github.wkw.share.base.adapter.ItemClickPresenter
import com.github.wkw.share.databinding.ActivityListBinding
import com.github.wkw.share.repository.PushService
import com.github.wkw.share.utils.ext.subscribeBy
import com.github.wkw.share.utils.extraDelegate
import com.github.wkw.share.vo.Follow
import com.uber.autodispose.autoDisposable
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import dagger.android.AndroidInjection
import javax.inject.Inject

class FollowActivity : BaseActivity<ActivityListBinding>(), ItemClickPresenter<Follow> {


    companion object {
        private const val TYPE = "IS_FANS"

        fun startActivity(context: Context, isFans: Boolean) {
            val intent = Intent(context, FollowActivity::class.java)
            intent.putExtra(TYPE, isFans)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var followViewModel: FollowViewModel
    @Inject
    lateinit var appExecutors: AppExecutors

    private val mAdapter by lazy {
        FollowAdapter(appExecutors).apply {
            itemPresenter = this@FollowActivity
        }
    }

    @Inject
    lateinit var pushService: PushService

    private val isFans: Boolean by extraDelegate(TYPE, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initBackToolbar(mBinding.toolbarLayout.toolbar, getString(R.string.follow))
        followViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(FollowViewModel::class.java)
        followViewModel.result.observe(this, Observer {
            mBinding.swLayout.isRefreshing = false
            mAdapter.submitList(it)
        })

        fetchData()
        mBinding.recyclerView.run {
            adapter = mAdapter
            addItemDecoration(
                    HorizontalDividerItemDecoration.Builder(context)
                            .colorResId(R.color.home_item_divider_color)
                            .sizeResId(R.dimen.home_divider_height)
                            .build()
            )
            layoutManager = LinearLayoutManager(context)
        }
        mBinding.swLayout.setOnRefreshListener {
            fetchData()
        }


    }


    private fun fetchData() {
        if (isFans) {
            followViewModel.fans()
        } else {
            followViewModel.myFollow()
        }

    }

    override fun getLayoutId() = R.layout.activity_list

    override fun onItemClick(v: View?, item: Follow) {
        followViewModel.follow(item.userId.toString())
                .autoDisposable(mScopeProvider)
                .subscribeBy(onNext = {
                    item.followed = it
                })
    }

}