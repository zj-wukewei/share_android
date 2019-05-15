package com.github.wkw.share.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.wkw.share.vo.Feed
import com.github.wkw.share.vo.Follow

/**
 * Created by GoGo on 2018/11/19.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */
@Database(entities = arrayOf(Follow::class, Feed::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun followDao(): FollowDao

    abstract fun feedDao(): FeedDao
}