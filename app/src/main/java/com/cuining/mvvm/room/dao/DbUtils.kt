package com.cuining.mvvm.room.dao

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cuining.mvvm.room.AppDatabase

/**
 * created by CuiNing 2020/12/30 :9:35
 */
object DbUtils {

    lateinit var db: AppDatabase

    fun init(application: Application) {
        db = Room.databaseBuilder(application, AppDatabase::class.java, "mvvm")
            .addMigrations(
                object : Migration(1, 2) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL("CREATE TABLE IF NOT EXISTS `test_c` (`id` INTEGER NOT NULL, `name` TEXT, PRIMARY KEY(`id`))")
                    }
                },
                object : Migration(2, 3) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL("CREATE TABLE IF NOT EXISTS `test_a` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `list` TEXT)")
                    }
                },
                object : Migration(3, 4) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL("DROP TABLE test_c")
                    }
                })
//            .allowMainThreadQueries() //允许在主线程中查询
            .build()
    }

    fun getArticleDao(): ArticleDao {
        return db.articleDao()
    }

    fun getTestADao(): TestADao {
        return db.testADao()
    }
}