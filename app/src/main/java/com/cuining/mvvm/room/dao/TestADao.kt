package com.cuining.mvvm.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cuining.mvvm.bean.TestABean

/**
 * created by CuiNing 2020/12/30 :10:59
 */
@Dao
interface TestADao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(test: TestABean)

    @Query("select * from test_a")
    fun getAll():List<TestABean>

}