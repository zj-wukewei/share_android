package com.github.wkw.share.ui.home

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.wkw.share.R
import com.github.wkw.share.base.BaseActivity
import com.github.wkw.share.databinding.ActivityFeedDetailBinding
import com.github.wkw.share.utils.ext.subscribeBy
import com.github.wkw.share.utils.extraDelegate
import com.uber.autodispose.autoDisposable
import dagger.android.AndroidInjection
import javax.inject.Inject

class HomeDetailActivity : BaseActivity<ActivityFeedDetailBinding>() {

    companion object {
        const val FEED_ID: String = "feedId"
        fun startActivity(context: Context, feedId: Int) {
            val intent = Intent(context, HomeDetailActivity::class.java)
            intent.putExtra(FEED_ID, feedId)
            context.startActivity(intent)
        }
    }

    private val feedId: Int by extraDelegate(HomeDetailActivity.FEED_ID, 0)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var feedDetailViewModel: FeedDetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        feedDetailViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(FeedDetailViewModel::class.java)

        feedDetailViewModel.feedDetail(feedId)
                .autoDisposable(mScopeProvider)
                .subscribeBy(
                        onNext = {
                            mBinding.feed = it
                        }
                )
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_feed_detail
    }
}