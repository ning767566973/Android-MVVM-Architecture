package com.cuining.mvvm.repository

import com.cuining.mvvm.bean.HomeListBean
import com.example.common.http.BaseResponse
import com.example.common.http.RetrofitClient
import com.cuining.mvvm.http.service.ArticleService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.RuntimeException

/**
 * @author: cuining
 * @date: 2020/4/17
 */
class ArticleRepository {

    suspend fun getArticleList(page: Int) = withContext(Dispatchers.IO) {
        //可以做缓存
        RetrofitClient.getService(ArticleService::class.java).getArticle(page)
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