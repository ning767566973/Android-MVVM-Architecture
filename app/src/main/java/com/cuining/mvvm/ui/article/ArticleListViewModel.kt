package com.cuining.mvvm.ui.article

import androidx.lifecycle.MutableLiveData
import com.cuining.mvvm.base.BaseViewModel
import com.cuining.mvvm.bean.HomeListBean
import com.cuining.mvvm.utils.InjectorUtil

/**
 * @author: cuining
 * @date: 2020/4/30
 */
class ArticleListViewModel : BaseViewModel() {

    var articleList = MutableLiveData<HomeListBean>()

    private val articleRepository by lazy { InjectorUtil.getArticleRepository() }

    fun getArticleList(page: Int) {
        launchOnlyResult(
            {
                articleRepository.getArticleList(page)
            },
            {
                articleList.value = it
            },
            complete = {
                defUI.refreshFinishEvent.call()
            },
            isShowDialog = false
        )
    }
}