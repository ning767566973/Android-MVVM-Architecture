package com.cuining.mvvm.utils

import com.cuining.mvvm.repository.ArticleRepository

object InjectorUtil {

    fun getArticleRepository() = ArticleRepository.getInstance()

}