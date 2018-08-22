package com.github.wkw.share.ui.follow

import android.support.v7.util.DiffUtil
import com.github.wkw.share.AppExecutors
import com.github.wkw.share.R
import com.github.wkw.share.base.adapter.SingleTypeListAdapter
import com.github.wkw.share.databinding.ItemFollowBinding
import com.github.wkw.share.vo.Follow

class FollowAdapter(appExecutors: AppExecutors): SingleTypeListAdapter<Follow, ItemFollowBinding>(R.layout.item_follow, appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<Follow>() {
            override fun areContentsTheSame(oldItem: Follow, newItem: Follow): Boolean {
                return oldItem.avatar == newItem.avatar && oldItem.nickname == newItem.nickname && oldItem.followed == newItem.followed
            }

            override fun areItemsTheSame(oldItem: Follow, newItem: Follow): Boolean {
                return newItem.id == oldItem.id
            }

        })