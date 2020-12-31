package com.cuining.mvvm

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.example.common.base.BaseActivity
import com.example.common.base.NoViewModel
import com.cuining.mvvm.ui.article.ArticleListActivity
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<NoViewModel, ViewDataBinding>() {
    override fun layoutId() = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        LiveEventBus.get("test",String::class.java)
            .observe(this, Observer {
                tvEventBus.text=it
            })
    }

    fun btnJump(view :View) {
        startActivity(Intent(this,ArticleListActivity::class.java))
    }
}
