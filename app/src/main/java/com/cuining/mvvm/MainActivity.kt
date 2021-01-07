package com.cuining.mvvm

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.cuining.mvvm.databinding.ActivityMainBinding
import com.example.common.base.BaseActivity
import com.example.common.base.NoViewModel
import com.cuining.mvvm.ui.article.ArticleListActivity
import com.cuining.mvvm.ui.test.*
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tbruyelle.rxpermissions3.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.function.Consumer

class MainActivity : BaseActivity<NoViewModel, ActivityMainBinding>() {
    override fun layoutId() = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        RxPermissions(this)
            .request(android.Manifest.permission.CAMERA)
            .subscribe {

            }
    }

    override fun initData() {
        LiveEventBus.get("test", String::class.java)
            .observe(this, Observer {
                tvEventBus.text = "接收到通知:$it"
            })
    }

    fun btnJump1(view: View) {
        LiveEventBus.get("test").post(UUID.randomUUID().toString())
    }

    fun btnJump2(view: View) {
        startActivity(Intent(this, ArticleListActivity::class.java))
    }

    fun btnJump3(view: View) {
        startActivity(Intent(this, RoomTestActivity::class.java))
    }

    fun btnJump4(view: View) {
        startActivity(Intent(this, NetTestActivity::class.java))

    }

    fun btnJump5(view: View) {
        startActivity(Intent(this, DataBindingTestActivity::class.java))
    }

    fun btnJump6(view: View) {
        startActivity(Intent(this, ViewModelTestActivity::class.java))

    }

    fun btnJump7(view: View) {
        startActivity(Intent(this, ObjectBoxActivity::class.java))

    }
}
