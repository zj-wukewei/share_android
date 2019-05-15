package com.github.wkw.share.db

import androidx.room.*
import com.github.wkw.share.vo.Follow
import io.reactivex.Flowable

/**
 * Created by GoGo on 2018/11/19.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */
@Dao
interface FollowDao {

    @Query("SELECT * from follow")
    fun getAll(): Flowable<List<Follow>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(follows: List<Follow>)

    @Delete
    fun delete(follow: Follow)

    @Query("DELETE FROM Follow")
    fun deleteAllFollows()
}