package com.github.wkw.share.ui.binds

import android.databinding.BindingAdapter
import android.support.v4.widget.SwipeRefreshLayout
import com.github.wkw.share.ui.page.PageViewModel
import com.github.wkw.share.widget.loadmore.RecyclerViewWithFooter

@BindingAdapter(value = "onRefresh")
fun bindOnRefresh(v: SwipeRefreshLayout, pageViewModel: PageViewModel<*>) {
    v.setOnRefreshListener { pageViewModel.loadData(true) }
}

@BindingAdapter(value = "loadMore")
fun bind(recyclerViewWithFooter: RecyclerViewWithFooter, pageViewModel: PageViewModel<*>) {
    recyclerViewWithFooter.setOnLoadMoreListener { pageViewModel.loadData(false) }
}
