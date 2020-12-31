package com.cuining.mvvm.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.cuining.mvvm.room.converter.TestBListConvert

/**
 * created by CuiNing 2020/12/30 :10:37
 */
@Entity(tableName = "test_a")
@TypeConverters(TestBListConvert::class)
data class TestABean(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var name:String?,
    var list:List<TestBBean>?
)
