package com.github.wkw.share.base.adapter

import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wkw.magicadapter.BR
import com.wkw.magicadapter.BindingViewHolder

/**
 * @author GoGo on 2018-11-26.
 */
class MagicPageAdapter<T : Any>(
        private val layoutId: Int,
        private val itemIds: ArrayList<Pair<(T) -> Int, (T) -> Any?>> = ArrayList(),
        private val handlers: ArrayList<Pair<Int, Any?>> = ArrayList(),
        diffCallback: DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {

            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
                    oldItem == newItem

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
                    oldItem == newItem
        }
) : PagedListAdapter<T, BindingViewHolder<ViewDataBinding>>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, postion: Int): BindingViewHolder<ViewDataBinding> {
        val viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
        )
        return BindingViewHolder(viewDataBinding!!)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<ViewDataBinding>, postion: Int) {
        val data = getItem(postion) as T
        holder.binding.setVariable(BR.item, data)

        val handlers = handlers
        for ((id, handle) in handlers) {
            holder.binding.setVariable(id, handle)
        }

        val itemIds = itemIds
        for ((idGet, setter) in itemIds) {
            val itemVariable = idGet(data)
            holder.binding.setVariable(itemVariable, setter(data))
        }
    }


    class Builder<T : Any> internal constructor(private val layoutId: Int) {
        private val itemIds: ArrayList<Pair<(T) -> Int, (T) -> Any?>> = ArrayList()
        private val handlers: ArrayList<Pair<Int, Any?>> = ArrayList()

        fun itemId(itemId: Int): Builder<T> {
            itemIds.add({ _: T -> itemId } to { i: T -> i })
            return this
        }

        fun handler(handlerId: Int, handler: Any): Builder<T> {
            handlers.add(handlerId to handler)
            return this
        }

        fun build(): MagicPageAdapter<T> {
            return MagicPageAdapter(layoutId, itemIds, handlers)
        }
    }
}