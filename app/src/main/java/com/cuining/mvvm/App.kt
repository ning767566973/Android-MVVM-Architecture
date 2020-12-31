package com.cuining.mvvm

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cuining.mvvm.room.AppDatabase
import com.cuining.mvvm.room.dao.DbUtils

/**
 * created by CuiNing 2020/12/29 :17:30
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DbUtils.init(this)

    }
}