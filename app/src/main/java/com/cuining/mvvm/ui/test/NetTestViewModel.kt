package com.cuining.mvvm.ui.test

import androidx.lifecycle.MutableLiveData
import com.cuining.mvvm.http.service.ArticleService
import com.cuining.mvvm.room.dao.DbUtils
import com.cuining.mvvm.utils.executeResponse
import com.example.common.base.BaseViewModel
import com.example.common.http.BaseResponse
import com.example.common.http.RetrofitClient
import com.google.gson.Gson
import kotlinx.coroutines.delay

/**
 * created by CuiNing 2020/12/31 :17:22
 */
class NetTestViewModel : BaseViewModel() {
    var oneResult = MutableLiveData<Any>()

    fun oneRequest() {
        launchOnlyResult({
            RetrofitClient.getService(ArticleService::class.java).getHotkey()
        }, {
            oneResult.value=it
        })
    }

    suspend fun net1(): String {
        delay(1000)
        return "1"
    }

    suspend fun net2(result1: String): Int {
        delay(1000)
        return result1.toInt()+1
    }

    suspend fun net3(): Int {
        delay(2000)
//        throw NullPointerException("xixixixixixii")
        return 3
    }
    suspend fun net4(): BaseResponse<Any?> {
        delay(2000)
        return BaseResponse("服务器炸了",404,null)
    }

    var moreResult=MutableLiveData<String>()
    fun moreRequest() {
        launchMoreRequest({
            var result1 = net1()
            moreResult.postValue("第1个请求结果：$result1")
            println("第1个请求结果：$result1")
            var result2 = net2(result1)
            moreResult.postValue("第2个请求结果：$result2")
            println("第2个请求结果：$result2")
            var result3 = net3()
            moreResult.postValue("第3个请求结果：$result3")
            println("第3个请求结果：$result3")
//            var result4 = net4().executeResponse()
//            println("第4个请求结果：$result4")
            var result5 = RetrofitClient.getService(ArticleService::class.java).getArticle(result2).executeResponse()
            //存数据库
            DbUtils.getArticleDao().insertAll(result5.datas)
            moreResult.postValue("最后一个请求结果：${Gson().toJson(result5)}")

            println("第5个请求结果：$result5")
        }, {
            moreResult.postValue("请求结果报错了${it.errMsg}")
            println("请求结果报错了${it.errMsg}")
        }, {
            moreResult.postValue("请求结果完毕")
            println("请求结果完毕")
        })
    }

}