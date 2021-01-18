package com.cuining.mvvm.ui.test

import androidx.lifecycle.MutableLiveData
import com.cuining.mvvm.http.service.ArticleService
import com.cuining.mvvm.room.dao.DbUtils
import com.cuining.mvvm.utils.executeResponse
import com.cuining.mvvm.utils.getArticleService
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
        launchOnlyResult(
            {
                //retrofit网络请求
                getArticleService().getHotkey()
            },
            {
                //返回data层
            },
            {
                //异常捕获
            },
            {
                //请求完成
            },
            isShowDialog = true,//是否显示加载框
            isShowErrorMsg = false//是否弹错误信息toast
        )
    }

    suspend fun net1(): String {
        delay(1000)
        return "1"
    }

    suspend fun net2(result1: String): Int {
        delay(1000)
        return result1.toInt() + 1
    }

    suspend fun net3(): Int {
        delay(2000)
//        throw NullPointerException("xixixixixixii")
        return 3
    }

    suspend fun net4(): BaseResponse<Any?> {
        delay(2000)
        return BaseResponse("服务器炸了", 404, null)
    }

    var moreResult = MutableLiveData<String>()
    fun moreRequest() {
        launchMoreRequest({
            var result1 = net1()
            moreResult.postValue("第1个请求结果：$result1")
            var result2 = net2(result1)
            moreResult.postValue("第2个请求结果：$result2")
            var result3 = getArticleService().getHotkey()
            moreResult.postValue("第3个请求结果：${Gson().toJson(result3)}")
            var result4 = net4().executeResponse()


            var result5 = getArticleService().getHotkey()

            //存数据库
//            DbUtils.getArticleDao().insertAll(result5.datas)
            moreResult.postValue("最后一个请求结果：${Gson().toJson(result5)}")
        }, {
            moreResult.value = "请求结果报错了${it.errMsg}"
        }, {
            moreResult.value = "请求结果完毕"
        })
    }

    fun more() {
        launchMoreRequest(
            {
                //多个retrofit网络请求 数据库存储
            },
            {
                //捕获异常
            },
            {
                //完成
            },
            isShowDialog = false,
            isShowErrorMsg = false
        )
    }

}