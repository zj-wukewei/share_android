package com.github.wkw.share.base.adapter

import androidx.paging.PagedListAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.annotation.CheckResult
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wkw.magicadapter.BR
import com.wkw.magicadapter.BindingViewHolder

/**
 * @author GoGo on 2018-11-26.
 */
class MagicPageAdapter<T : Any, DB : ViewDataBinding>(
        private val layoutId: Int,
        private val itemIds: ArrayList<Pair<(T) -> Int, (T) -> Any?>> = ArrayList(),
        private val handlers: ArrayList<Pair<Int, Any?>> = ArrayList(),
        val callback: (T, DB, Int) -> Unit = { _, _, _ -> },
        diffCallback: DiffUtil.ItemCallback<T>) : PagedListAdapter<T, BindingViewHolder<ViewDataBinding>>(diffCallback) {
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

        callback(data, holder.binding as DB, postion)
    }


    class Builder<T : Any, DB : ViewDataBinding> internal constructor(private val layoutId: Int,
                                                private val diffCallback: DiffUtil.ItemCallback<T>) {
        private val itemIds: ArrayList<Pair<(T) -> Int, (T) -> Any?>> = ArrayList()
        private val handlers: ArrayList<Pair<Int, Any?>> = ArrayList()
        private var callback: (T, DB, Int) -> Unit = { _, _, _ -> }

        fun itemId(itemId: Int): Builder<T, DB> {
            itemIds.add({ _: T -> itemId } to { i: T -> i })
            return this
        }

        fun handler(handlerId: Int, handler: Any): Builder<T, DB> {
            handlers.add(handlerId to handler)
            return this
        }

        fun setCallback(callback: (T, DB, Int) -> Unit = { _, _, _ -> }): Builder<T, DB> {
            this.callback = callback
            return this

        }

        fun build(): MagicPageAdapter<T, DB> {
            return MagicPageAdapter(layoutId, itemIds, handlers, callback,  diffCallback)
        }
    }
}