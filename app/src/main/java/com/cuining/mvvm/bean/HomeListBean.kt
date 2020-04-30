package com.cuining.mvvm.bean

import com.cuining.mvvm.bean.ArticlesBean

/**
 * @author: cuining
 * @date: 2020/4/26
 */
data class HomeListBean(
    val curPage: Int,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val datas: List<ArticlesBean>
)