package com.cuining.mvvm.repository

import com.cuining.mvvm.bean.HomeListBean
import com.cuining.mvvm.http.BaseResponse
import com.cuining.mvvm.http.RetrofitClient
import com.cuining.mvvm.http.service.ArticleService

/**
 * @author: cuining
 * @date: 2020/4/17
 */
class ArticleRepository {

    suspend fun getArticleList(page:Int): BaseResponse<HomeListBean> {
        //可以做缓存
        return RetrofitClient.getService(ArticleService::class.java).getArticle(page)
    }

    companion object {
        @Volatile
        private var INSTANCE: ArticleRepository? = null

        fun getInstance() =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ArticleRepository().also { INSTANCE = it }
            }
    }

}