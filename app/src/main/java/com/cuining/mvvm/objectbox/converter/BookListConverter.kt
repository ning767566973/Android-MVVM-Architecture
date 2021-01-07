package com.cuining.mvvm.objectbox.converter

import com.cuining.mvvm.bean.Book
import com.cuining.mvvm.bean.TestBBean
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.objectbox.converter.PropertyConverter
import java.lang.reflect.Type

/**
 * created by CuiNing 2021/1/7 :10:02
 */
class BookListConverter:PropertyConverter<List<Book>,String>{
    override fun convertToEntityProperty(json: String?): List<Book> {
        val gson = Gson()
        if (json == null) {
            return emptyList()
        }
        val listType: Type = object : TypeToken<List<Book?>?>() {}.type
        return gson.fromJson(json, listType)
    }

    override fun convertToDatabaseValue(entityProperty: List<Book>?): String {
        return Gson().toJson(entityProperty)
    }
}