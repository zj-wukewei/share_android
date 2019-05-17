package com.github.wkw.share.ui.follow

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.wkw.share.BR
import com.github.wkw.share.R
import com.github.wkw.share.base.BaseActivity
import com.github.wkw.share.base.adapter.ItemClickPresenter
import com.github.wkw.share.base.adapter.MagicPageAdapter
import com.github.wkw.share.databinding.ActivityListBinding
import com.github.wkw.share.databinding.ItemFollowBinding
import com.github.wkw.share.utils.ext.subscribeBy
import com.github.wkw.share.utils.extraDelegate
import com.github.wkw.share.vo.Follow
import com.uber.autodispose.autoDisposable
import com.wkw.magicadapter.MagicAdapter
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import dagger.android.AndroidInjection
import timber.log.Timber
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



    private val mAdapter by lazy {
        MagicAdapter.repositoryAdapter()
                .addItemDsl<Follow, ItemFollowBinding> {
                    resId = R.layout.item_follow
                    handler(BR.presenter, this@FollowActivity)
                    areContentsTheSame = { oldItem, newItem -> oldItem.avatar == newItem.avatar && oldItem.nickname == newItem.nickname && oldItem.followed == newItem.followed }
                    areItemsTheSame = { oldItem, newItem -> newItem.id == oldItem.id }
                }
                .build()
    }

    private val isFans: Boolean by extraDelegate(TYPE, false)


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initBackToolbar(mBinding.toolbarLayout.toolbar, getString(R.string.follow))
        followViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(FollowViewModel::class.java)
        followViewModel.result.observe(this, Observer { it ->
            mBinding.swLayout.isRefreshing = false
            it?.let {
                Timber.d("follows size %s", it.size.toString())
                mAdapter.submitList(it)
            }
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
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
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

    override fun onItemClick(item: Follow) {
        followViewModel.follow(item)
                .autoDisposable(mScopeProvider)
                .subscribeBy(onNext = {
                    item.followed = it
                })
    }

}