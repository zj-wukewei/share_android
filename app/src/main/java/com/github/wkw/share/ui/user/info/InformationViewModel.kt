package com.github.wkw.share.ui.user.info

import androidx.lifecycle.MutableLiveData
import com.github.wkw.share.repository.CategoryRepository
import com.github.wkw.share.repository.UserRepository
import com.github.wkw.share.utils.ext.subscribeBy
import com.github.wkw.share.utils.ext.toFlowable
import com.github.wkw.share.utils.toast
import com.github.wkw.share.viewmodel.AutoDisposeViewModel
import com.github.wkw.share.vo.Category
import com.github.wkw.share.vo.Errors
import com.github.wkw.share.vo.Resource
import com.github.wkw.share.vo.UserInfo
import com.uber.autodispose.autoDisposable
import io.reactivex.rxkotlin.Observables
import javax.inject.Inject

/**
 * Created by GoGo on 2018/11/15.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */
class InformationViewModel @Inject constructor(private val userRepository: UserRepository, categoryRepository: CategoryRepository) : AutoDisposeViewModel() {

    val information = MutableLiveData<InformationVo>()
    val updateSuccess = MutableLiveData<Boolean>()
    val error = MutableLiveData<Errors>()

    init {
        Observables.zip(userRepository.userInfo(), categoryRepository.categoryList())
        { t1, t2 -> InformationVo(t1, t2) }
                .autoDisposable(this)
                .subscribeBy {
                    information.value = it
                }

        error.toFlowable()
                .autoDisposable(this)
                .subscribeBy { error ->
                    error.message?.let {
                        toast { it }
                    }
                }
    }


    fun updateInfo() {
        val userInfo = information.value?.userInfo
        userInfo?.let {

            if (it.nickname.isEmpty()) {
                error.postValue(Errors.EmptyInputError("昵称 can't be null"))
                return
            }

            if (it.gender == null) {
                error.postValue(Errors.EmptyInputError("性别 can't be null"))
                return
            }

            if (it.categoryId == null) {
                error.postValue(Errors.EmptyInputError("宠物 can't be null"))
                return
            }

            userRepository.updateInfo(it)
                    .autoDisposable(this)
                    .subscribeBy(onComplete = {
                        updateSuccess.postValue(true)
                    })
        }
    }

}


class InformationVo(val userInfo: UserInfo, val categoryList: List<Category>)

