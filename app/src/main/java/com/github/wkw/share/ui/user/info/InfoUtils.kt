package com.github.wkw.share.ui.user.info

import android.widget.TextView
import com.github.wkw.share.R
import com.github.wkw.share.vo.Category

/**
 * Created by GoGo on 2018/11/15.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */
object InfoUtils {

    @JvmStatic
    fun transformGender(view: TextView, gender: Int): String {
        return when (gender) {
            1 -> view.context.getString(R.string.male)
            2 -> view.context.getString(R.string.female)
            else -> view.context.getString(R.string.unknown)
        }
    }

    @JvmStatic
    fun transformCategory(categoryId: Int?, categoryList: List<Category>?): String {
        if (categoryId == null || categoryList == null) return ""
        val item = categoryList.find { item -> item.id == categoryId } ?: return ""
        return item.name
    }

}