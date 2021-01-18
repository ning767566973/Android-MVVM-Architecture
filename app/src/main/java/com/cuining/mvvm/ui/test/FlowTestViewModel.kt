package com.cuining.mvvm.ui.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.example.common.base.BaseViewModel
import kotlinx.coroutines.flow.debounce

/**
 * created by CuiNing 2021/1/11 :14:31
 */
class FlowTestViewModel : BaseViewModel() {
    var search = MutableLiveData<String>()
//    init {
//
//        val asLiveData = search.asFlow()
//            .debounce(1000)
//            .asLiveData()
//    }
}