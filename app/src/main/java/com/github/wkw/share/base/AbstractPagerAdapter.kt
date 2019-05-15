package com.github.wkw.share.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


abstract class AbstractPagerAdapter(fm: androidx.fragment.app.FragmentManager, var title: Array<String>) : androidx.fragment.app.FragmentStatePagerAdapter(fm) {
    var list: MutableList<androidx.fragment.app.Fragment?> = mutableListOf()

    init {
        title.iterator().forEach { list.add(null) }
    }

    override fun getCount(): Int = title.size

    abstract override fun getItem(pos: Int): androidx.fragment.app.Fragment?

    override fun getPageTitle(position: Int): CharSequence = title[position]

}