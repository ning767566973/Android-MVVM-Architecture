package com.cuining.mvvm

import android.app.Application
import com.cuining.mvvm.objectbox.ObjectBox
import com.cuining.mvvm.room.dao.DbUtils
import com.tencent.mmkv.MMKV


/**
 * created by CuiNing 2020/12/29 :17:30
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DbUtils.init(this)
        val rootDir = MMKV.initialize(this)

        ObjectBox.init(this)
    }
}