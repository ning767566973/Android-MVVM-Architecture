package com.cuining.mvvm.ui.article

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.cuining.mvvm.R
import com.example.common.base.BaseActivity
import com.example.common.base.NoViewModel
import kotlinx.android.synthetic.main.activity_article_detail.*

class ArticleDetailActivity : BaseActivity<NoViewModel, ViewDataBinding>() {

    companion object {
        fun start(context: Context, url: String) {
            context.startActivity(Intent(context, ArticleDetailActivity::class.java).apply {
                putExtra("url", url)
            })
        }
    }

    private val url: String by lazy { intent.getStringExtra("url") }

    override fun layoutId() = R.layout.activity_article_detail

    override fun initView(savedInstanceState: Bundle?) {
        webView.loadUrl(url)
    }

    override fun initData() {

    }

}