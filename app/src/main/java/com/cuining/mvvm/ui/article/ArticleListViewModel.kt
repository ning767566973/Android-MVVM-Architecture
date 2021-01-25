package com.cuining.mvvm.ui.article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.base.BaseViewModel
import com.cuining.mvvm.bean.HomeListBean
import com.example.common.http.BaseResponse
import com.cuining.mvvm.room.dao.DbUtils
import com.cuining.mvvm.utils.InjectorUtil
import com.cuining.mvvm.utils.executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author: cuining
 * @date: 2020/4/30
 */
class ArticleListViewModel : BaseViewModel() {

    var articleList = MutableLiveData<HomeListBean>()

    private val articleRepository by lazy { InjectorUtil.getArticleRepository() }

    suspend fun net1(): String {
        delay(1000)
        return "result1"
    }

    suspend fun net2(): Int {
        delay(1000)
        return 1
    }

    suspend fun net3(): Int {
        delay(2000)
//        throw NullPointerException("xixixixixixii")
        return 1
    }

    suspend fun net4(): BaseResponse<Any?> {
        delay(2000)
        return BaseResponse("服务器炸了", 404, null)
    }

    fun test() {
        diyuhuidiao({
            var result1 = net1()
            println("第1个请求结果：$result1")
            var result2 = net2()
            println("第2个请求结果：$result2")
            var result3 = net2()
            println("第3个请求结果：$result3")
            var result4 = net4().executeResponse()
            println("第4个请求结果：$result4")
            var result5 = articleRepository.getArticleList(result2).executeResponse()
            //存数据库
            DbUtils.getArticleDao()?.insertAll(result5.datas)
            println("第5个请求结果：$result5")
        }, {
            println("请求结果报错了${it.errMsg}")
        }, {
            println("请求结果完毕")
        })
    }


    fun getArticleList(page: Int) {
        launch(
            {
                articleRepository.getArticleList(page)
            },
            {
                articleList.value = it
            },
            complete = {
                defUI.refreshFinishEvent.call()
            }
        )
//        launchOnlyResult(
//            {
//                articleRepository.getArticleList(page)
//            },
//            {
//                articleList.value = it
//
//                viewModelScope.launch {
//                    withContext(Dispatchers.IO){
//                        DbUtils.getArticleDao().insertAll(it.datas)
//                    }
//                }
//
//
//            },
//            complete = {
//                defUI.refreshFinishEvent.call()
//            },
//            isShowDialog = false
//        )
    }
}