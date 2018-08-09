package com.github.wkw.share.base.adapter

import android.view.View

interface ItemClickPresenter<in Any> {
    fun onItemClick(v: View? = null, item: Any)
}