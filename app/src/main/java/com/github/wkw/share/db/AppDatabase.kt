package com.github.wkw.share.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.github.wkw.share.vo.Follow

/**
 * Created by GoGo on 2018/11/19.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */
@Database(entities = arrayOf(Follow::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun followDao(): FollowDao
}