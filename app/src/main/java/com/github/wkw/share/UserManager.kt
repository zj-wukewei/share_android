package com.github.wkw.share

import android.content.Context
import com.github.wkw.share.api.reponse.UserEntity
import com.github.wkw.share.extens.mySharedPreferences


class UserManager constructor(val context: Context) {

    lateinit var token: String
    lateinit var uId: String

    companion object {
        private const val TOKEN: String = "TOKEN"
        private const val UID: String = "UID"
    }


    init {
        context.mySharedPreferences().run {
            token = getString(TOKEN, "")
            uId = getString(UID, "")
        }
    }

    fun setUserEntity(userEntity: UserEntity) {
        token = userEntity.token
        uId = userEntity.uId
        context.mySharedPreferences().edit()
                .putString(TOKEN, token)
                .putString(UID, uId)
                .apply()
    }
}