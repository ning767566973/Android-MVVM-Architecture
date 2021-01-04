package com.cuining.mvvm.ui.test

import androidx.lifecycle.viewModelScope
import com.cuining.mvvm.bean.TestABean
import com.cuining.mvvm.bean.TestBBean
import com.cuining.mvvm.room.dao.DbUtils
import com.example.common.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * created by CuiNing 2020/12/31 :15:08
 */
class RoomTestViewModel :BaseViewModel() {

    fun insertOrUpdate(id: String, name: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                DbUtils.getTestADao().insert(TestABean(if (id.isNotEmpty()) id.toInt() else 0, name, arrayListOf(
                    TestBBean("xixi",12)
                )))
            }
        }
    }

    fun deleteTest(testABean: TestABean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                DbUtils.getTestADao().delete(testABean)
            }
        }

    }

}