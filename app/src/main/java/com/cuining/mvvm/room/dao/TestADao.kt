package com.cuining.mvvm.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cuining.mvvm.bean.TestABean

/**
 * created by CuiNing 2020/12/30 :10:59
 */
@Dao
interface TestADao :BaseDao<TestABean>{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(test: TestABean)

    @Query("select * from test_a")
    fun getAll(): LiveData<List<TestABean>>

}