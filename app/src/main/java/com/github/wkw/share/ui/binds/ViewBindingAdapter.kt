package com.github.wkw.share.ui.binds

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.wkw.share.R
import com.github.wkw.share.base.page.PageViewModel

@BindingAdapter(value = ["onRefresh"])
fun bindOnRefresh(v: SwipeRefreshLayout, pageViewModel: PageViewModel<*, Any>?) {
    pageViewModel?.let {
        v.setOnRefreshListener { it.onSwipeRefresh() }
    }
}

@BindingAdapter(value = ["loadMore"])
fun bind(recyclerView: RecyclerView, pageViewModel: PageViewModel<*, Any>?) {
    pageViewModel?.let {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView!!.layoutManager is LinearLayoutManager) {
                    //表示是否能向上滚动，false表示已经滚动到底部
                    //防止多次拉取同样的数据
                    if (recyclerView.canScrollVertically(1)) {
                        if (it.hasMore.value == true) {
                            it.loadMore()
                        }
                    }
                }
            }
        })
    }
}

@BindingAdapter(value = ["url", "placeholder"], requireAll = false)
fun loadImageUrl(imageView: ImageView, imageUri: String?, placeholder: Drawable?) {
    val placeholderDrawable = placeholder ?: AppCompatResources.getDrawable(
            imageView.context, R.drawable.generic_placeholder
    )
    when (imageUri) {
        null -> {
            Glide.with(imageView)
                    .load(placeholderDrawable)
                    .into(imageView)
        }
        else -> {
            Glide.with(imageView)
                    .load(imageUri)
                    .apply(RequestOptions().placeholder(placeholderDrawable))
                    .into(imageView)
        }
    }
}

@BindingAdapter("visibleGone")
fun showHide(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}