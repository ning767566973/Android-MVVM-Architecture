package com.cuining.mvvm.bean

import com.cuining.mvvm.objectbox.converter.BookListConverter
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * created by CuiNing 2021/1/6 :11:16
 */
@Entity
data class Student(
    @Id(assignable = true)
    var id: Long = 0,
    var name: String?,
    var age: Int? = 0,
    @Convert(converter = BookListConverter::class, dbType = String::class)
    var books: List<Book>? = null
)
