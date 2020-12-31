package com.cuining.mvvm.http.service


import com.cuining.mvvm.bean.HomeListBean
import com.example.common.http.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author: cuining
 * @date: 2020/4/17
 */
interface ArticleService {

    @GET("article/listproject/{page}/json")
    suspend fun getArticle(@Path("page")page: Int): BaseResponse<HomeListBean>
}