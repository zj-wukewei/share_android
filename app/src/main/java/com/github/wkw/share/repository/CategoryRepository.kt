package com.github.wkw.share.repository

import com.github.wkw.share.AppExecutors
import com.github.wkw.share.api.ShareService
import com.github.wkw.share.vo.Category
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by GoGo on 2018/11/15.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */
class CategoryRepository @Inject constructor(private val shareService: ShareService, private val appExecutors: AppExecutors) {

    /***
     * 获取动物分类
     * @author GoGo
     * @param
     * @return
     */
    fun categoryList(): Observable<List<Category>> {
        return shareService.categoryList()
                .compose(appExecutors.ioMainScheduler())
                .compose(RepositoryUtils.handleResult())
    }
}