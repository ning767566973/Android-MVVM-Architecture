package com.cuining.mvvm.room.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cuining.mvvm.bean.ArticlesBean

/**
 * created by CuiNing 2020/12/29 :17:21
 */
@Dao
interface ArticleDao {

    @get:Query("select * from articlesbean")
    val all:List<ArticlesBean?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articlesBean: List<ArticlesBean>?)

    @Query("select * from articlesbean order by publishTime desc limit 10 offset :page*10")
    fun getArticles(page:Int): List<ArticlesBean>

}