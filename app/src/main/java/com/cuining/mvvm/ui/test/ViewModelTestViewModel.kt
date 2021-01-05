package com.cuining.mvvm.ui.test

import androidx.lifecycle.MutableLiveData
import com.example.common.base.BaseViewModel
import java.util.*

/**
 * created by CuiNing 2021/1/5 :10:28
 */
class ViewModelTestViewModel : BaseViewModel() {

    var info = MutableLiveData<String>()
    fun getInfo() {
        info.value = UUID.randomUUID().toString()
    }



}