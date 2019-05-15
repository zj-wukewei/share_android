package com.github.wkw.share.ui.user.info

import androidx.lifecycle.MutableLiveData
import com.github.wkw.share.repository.CategoryRepository
import com.github.wkw.share.repository.UserRepository
import com.github.wkw.share.utils.ext.subscribeBy
import com.github.wkw.share.viewmodel.AutoDisposeViewModel
import com.github.wkw.share.vo.Category
import com.github.wkw.share.vo.UserInfo
import com.uber.autodispose.autoDisposable
import io.reactivex.rxkotlin.Observables
import javax.inject.Inject

/**
 * Created by GoGo on 2018/11/15.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */
class InformationViewModel @Inject constructor(userRepository: UserRepository, categoryRepository: CategoryRepository) : AutoDisposeViewModel() {

    val information = MutableLiveData<InformationVo>()

    init {
        Observables.zip(userRepository.userInfo(), categoryRepository.categoryList())
        { t1, t2 -> InformationVo(t1, t2) }
                .autoDisposable(this)
                .subscribeBy {
                    information.value = it
                }
    }


}


class InformationVo(val userInfo: UserInfo, val categoryList: List<Category>)