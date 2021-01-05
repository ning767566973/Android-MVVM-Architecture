package com.cuining.mvvm.ui.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * created by CuiNing 2021/1/4 :17:54
 */
class DataBindingViewModel : BaseViewModel() {

    var result = MutableLiveData<String>()
    fun request(toString: String) {
        //模拟一个网络请求
        viewModelScope.launch {
            delay(5000)
            result.value="数据改变：·$toString"
        }
    }

}