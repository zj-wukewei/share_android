package com.github.wkw.share.ui.binds

import android.databinding.BindingAdapter
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import com.github.wkw.share.ui.page.PageViewModel
import com.github.wkw.share.widget.loadmore.OnLoadMoreListener
import com.github.wkw.share.widget.loadmore.RecyclerViewWithFooter

@BindingAdapter(value = "onRefresh")
fun bindOnRefresh(v: SwipeRefreshLayout, pageViewModel: PageViewModel) {
    v.setOnRefreshListener { pageViewModel.onSwipeRefresh() }
}

@BindingAdapter(value = "loadMore")
fun bind(recyclerView: RecyclerView, pageViewModel: PageViewModel) {
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (recyclerView!!.layoutManager is LinearLayoutManager) {
                //表示是否能向上滚动，false表示已经滚动到底部
                //防止多次拉取同样的数据
                if (!recyclerView.canScrollVertically(1) ) {
                    if (pageViewModel.hasMore.value == true) {
                        pageViewModel.loadMore()
                    }
                }
            }
        }
    })
}
