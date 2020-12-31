package com.cuining.mvvm.ui.article

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.cuining.mvvm.R
import com.example.common.base.BaseActivity
import com.cuining.mvvm.bean.ArticlesBean
import com.cuining.mvvm.utils.finishRefreshAndLoadMore
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_article_list.*
import java.util.*

class ArticleListActivity : BaseActivity<ArticleListViewModel, ViewDataBinding>() {

    private val mAdapter by lazy { ArticleListAdapter() }

    private var page = 1

    override fun layoutId() = R.layout.activity_article_list

    override fun initView(savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter

        viewModel.defUI.refreshFinishEvent.observe(this, Observer {
            refreshLayout.finishRefreshAndLoadMore()
        })
        viewModel.articleList.observe(this, Observer {
            if (it.curPage == 1) {
                mAdapter.setNewInstance(it.datas as MutableList<ArticlesBean>)
            } else {
                mAdapter.addData(it.datas)
                refreshLayout.finishRefreshAndLoadMore()
                page++
            }
            refreshLayout.setNoMoreData(it.over)
        })

        with(refreshLayout) {
            setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    getArticleList(page)
                }

                override fun onRefresh(refreshLayout: RefreshLayout) {
                    refreshLayout.resetNoMoreData()
                    page = 1
                    getArticleList(page)
                }
            })
            autoRefresh()
        }
    }

    private fun getArticleList(page: Int) {
        viewModel.getArticleList(page)
    }

    override fun initData() {
    }

}
