package com.github.wkw.share.ui.home

import android.support.v7.util.DiffUtil
import com.github.wkw.share.AppExecutors
import com.github.wkw.share.R
import com.github.wkw.share.base.adapter.SingleTypeListAdapter
import com.github.wkw.share.databinding.ItemHomeBinding
import com.github.wkw.share.vo.Feed

class HomeAdapter(appExecutors: AppExecutors) : SingleTypeListAdapter<Feed, ItemHomeBinding>(R.layout.item_home, appExecutors, diffCallback = object : DiffUtil.ItemCallback<Feed>() {
    override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem.content == newItem.content && oldItem.images == newItem.images
    }

})