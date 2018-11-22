package com.github.wkw.share.ui.home

import com.github.wkw.share.repository.FeedRepository
import com.github.wkw.share.viewmodel.AutoDisposeViewModel
import javax.inject.Inject

/**
 * Created by GoGo on 2018/11/22.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */
class FeedDetailViewModel @Inject constructor(private val feedRepository: FeedRepository) : AutoDisposeViewModel() {

    fun feedDetail(feedId: Int) = feedRepository.feedDetail(feedId)
}