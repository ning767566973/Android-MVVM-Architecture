package com.cuining.mvvm.ui.article

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.cuining.mvvm.R
import com.example.common.base.BaseActivity
import com.cuining.mvvm.bean.ArticlesBean
import com.cuining.mvvm.room.dao.DbUtils
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

        with(viewModel) {
            defUI.refreshFinishEvent.observe(this@ArticleListActivity, Observer {
                refreshLayout.finishRefreshAndLoadMore()
            })
            articleList.observe(this@ArticleListActivity, Observer {
                if (it.curPage == 1) {
                    mAdapter.setNewInstance(it.datas as MutableList<ArticlesBean>)
                } else {
                    mAdapter.addData(it.datas)
                    refreshLayout.finishRefreshAndLoadMore()
                    page++
                }
                refreshLayout.setNoMoreData(it.over)
            })
            articleListDb.observe(this@ArticleListActivity, Observer {
                mAdapter.setNewInstance(it as MutableList<ArticlesBean>)
            })
        }

        with(refreshLayout) {
            setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    viewModel.getArticleList(page)
                }

                override fun onRefresh(refreshLayout: RefreshLayout) {
                    refreshLayout.resetNoMoreData()
                    page = 1
                    viewModel.getArticleList(page)
                }
            })
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            (adapter.data[position] as ArticlesBean).link?.let {
                ArticleDetailActivity.start(this, it)
            }
        }
    }


    override fun initData() {
        //先从数据库拿了一页回来
        viewModel.getArticleListFromDb()
        //刷新
        refreshLayout.autoRefresh()
    }

}
