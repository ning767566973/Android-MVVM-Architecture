package com.example.common.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.event.SingleLiveEvent
import com.example.common.http.BaseResponse
import com.example.common.http.ExceptionHandle
import com.example.common.http.ResponseThrowable
import kotlinx.coroutines.*
import java.lang.Exception

/**
 * @author: cuining
 * @date: 2020/4/17
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {

    val defUI: UIChange by lazy { UIChange() }

    /**
     * 所有网络请求都在 viewModelScope 域中启动，当页面销毁时会自动
     * 调用ViewModel的  #onCleared 方法取消所有协程
     */
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch { block() }

    fun launchIO(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                block()
            }
        }
    }
    /**
     * 过滤请求结果，其他全抛异常
     * @param block 请求体
     * @param success 成功回调
     * @param error 失败回调
     * @param complete  完成回调（无论成功失败都会调用）
     * @param isShowDialog 是否显示加载框
     */
    fun <T> launchOnlyResult(
        block: suspend CoroutineScope.() -> BaseResponse<T>,
        success: (T) -> Unit,
        error: (ResponseThrowable) -> Unit = {
        },
        complete: () -> Unit = {},
        isShowDialog: Boolean = true,
        isShowErrorMsg: Boolean = true
    ) {
        if (isShowDialog) defUI.showDialog.call()
        launchUI {
            handleException(
                { withContext(Dispatchers.IO) { block() } },
                { res ->
                    executeResponse(res) { success(it) }
                },
                {
                    if (isShowErrorMsg) {
                        defUI.toastEvent.postValue("${it.code}:${it.errMsg}")
                    }
                    error(it)
                },
                {
                    defUI.dismissDialog.call()
                    complete()
                }
            )
        }
    }

    /**
     * 处理多个网络请求
     */
    fun launchMoreRequest(
        gan: suspend CoroutineScope.() -> Unit,
        error: (ResponseThrowable) -> Unit = {},
        complete: () -> Unit = {},
    ) {
        viewModelScope.launch {
            coroutineScope {
                try {
                    withContext(Dispatchers.IO){
                        gan()
                    }
                } catch (e: Exception) {
                    error(ExceptionHandle.handleException(e))
                } finally {
                    complete()
                }
            }
        }
    }

    /**
     * 请求结果过滤
     */
    private suspend fun <T> executeResponse(
        response: BaseResponse<T>,
        success: suspend CoroutineScope.(T) -> Unit
    ) {
        coroutineScope {
            if (response.errorCode == 0) success(response.data)
            else throw ResponseThrowable(response.errorCode!!, response.errorMsg!!)
        }
    }

    /**
     * 异常统一处理
     */
    private suspend fun <T> handleException(
        block: suspend CoroutineScope.() -> BaseResponse<T>,
        success: suspend CoroutineScope.(BaseResponse<T>) -> Unit,
        error: suspend CoroutineScope.(ResponseThrowable) -> Unit,
        complete: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                success(block())
            } catch (e: Throwable) {
                error(ExceptionHandle.handleException(e))
            } finally {
                complete()
            }
        }
    }


    /**
     * UI事件
     */
    inner class UIChange {
        val showDialog by lazy { SingleLiveEvent<String>() }
        val dismissDialog by lazy { SingleLiveEvent<Void>() }
        val toastEvent by lazy { SingleLiveEvent<String>() }
        val refreshFinishEvent by lazy { SingleLiveEvent<Void>() }
    }
}