package com.cuining.mvvm.objectbox

import android.app.Application
import android.util.Log
import com.cuining.mvvm.BuildConfig
import com.cuining.mvvm.bean.MyObjectBox
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import io.objectbox.sync.Sync

/**
 * created by CuiNing 2021/1/6 :11:02
 */
object ObjectBox {
    lateinit var boxStore: BoxStore
    fun init(application: Application) {
        boxStore = MyObjectBox.builder()
            .androidContext(application)
            .build()

        if (BuildConfig.DEBUG) {
            AndroidObjectBrowser(boxStore).start(application)
        }
    }
}