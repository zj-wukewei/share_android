package com.github.wkw.share.base.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.recyclerview.extensions.AsyncDifferConfig
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater.from
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import com.github.wkw.share.AppExecutors

open class SingleTypeListAdapter<T, V : ViewDataBinding>(
        private val layoutId: Int,
        appExecutors: AppExecutors,
        diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BindingViewHolder<V>>(
        AsyncDifferConfig.Builder<T>(diffCallback)
                .setBackgroundThreadExecutor(appExecutors.diskIO())
                .build()
) {

    var itemPresenter: ItemClickPresenter<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<V> {
        val binding: V = DataBindingUtil.inflate(from(parent.context), layoutId, parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<V>, position: Int) {
        val item: T = getItem(position)
        holder.binding.setVariable(BR.item, item)
        holder.binding.setVariable(BR.presenter, itemPresenter)
    }
}