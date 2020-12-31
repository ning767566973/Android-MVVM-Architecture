package com.cuining.mvvm.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cuining.mvvm.bean.ArticlesBean
import com.cuining.mvvm.bean.TestABean
import com.cuining.mvvm.room.dao.ArticleDao
import com.cuining.mvvm.room.dao.TestADao

/**
 * created by CuiNing 2020/12/29 :17:26
 */
@Database(
    entities = [ArticlesBean::class, TestABean::class],
    version = 4
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun testADao(): TestADao
}