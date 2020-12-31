package com.cuining.mvvm.room.converter

import androidx.room.TypeConverter
import com.cuining.mvvm.bean.TestBBean
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**
 * created by CuiNing 2020/12/30 :10:44
 */
class TestBListConvert {
    @TypeConverter
    fun stringToList(value: String?):List<TestBBean>{
        val gson = Gson()
        if (value == null) {
            return emptyList()
        }
        val listType: Type = object : TypeToken<List<TestBBean?>?>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun listToString(value :List<TestBBean?>?) :String{
        return Gson().toJson(value)
    }
}