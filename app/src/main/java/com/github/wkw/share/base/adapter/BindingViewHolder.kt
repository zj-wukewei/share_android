package com.github.wkw.share.base.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

class BindingViewHolder<T : ViewDataBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)