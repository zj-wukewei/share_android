package com.github.wkw.share.db

import androidx.room.*
import com.github.wkw.share.vo.Feed
import com.github.wkw.share.vo.Follow
import io.reactivex.Maybe

/**
 * Created by GoGo on 2018/11/19.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */
@Dao
interface FeedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(feed: Feed)

    @Query("SELECT * FROM feed WHERE id =:feedId ")
    fun selectByFeedId(feedId: Int): Maybe<Feed>

    @Delete
    fun delete(follow: Follow)

}