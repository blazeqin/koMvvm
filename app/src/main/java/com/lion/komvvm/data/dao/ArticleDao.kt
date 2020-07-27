package com.lion.komvvm.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lion.komvvm.entity.ArticlesBean
import com.lion.komvvm.entity.NavTypeBean

/**
 * The Data Access Object for the [ArticlesBean] class.
 */
@Dao
interface ArticleDao :BaseDao<List<ArticlesBean>> {

    //cannot use suspend with livedata
    //get data counts: pageSize; from id: offset.
    @Query("SELECT * FROM articles LIMIT :pageSize OFFSET :offset")
    suspend fun getArticles(pageSize: Int, offset: Int): List<ArticlesBean>

    @Query("SELECT * FROM articles WHERE chapter_id=:chapterId LIMIT :pageSize OFFSET :offset")
    suspend fun getArticlesById(pageSize: Int, offset: Int, chapterId: Int): List<ArticlesBean>
}