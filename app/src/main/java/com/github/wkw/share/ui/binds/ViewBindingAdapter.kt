package com.github.wkw.share.ui.binds

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.content.res.AppCompatResources
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.wkw.share.R
import com.github.wkw.share.base.page.PageViewModel

@BindingAdapter(value = ["onRefresh"])
fun bindOnRefresh(v: SwipeRefreshLayout, pageViewModel: PageViewModel<*>?) {
    pageViewModel?.let {
        v.setOnRefreshListener { it.initDataRepository() }
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