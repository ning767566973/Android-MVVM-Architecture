package com.cuining.mvvm.ui.test

import com.cuining.mvvm.bean.Book
import com.cuining.mvvm.bean.Student
import com.cuining.mvvm.objectbox.ObjectBox
import com.example.common.base.BaseViewModel
import com.google.gson.Gson
import io.objectbox.relation.ToMany

/**
 * created by CuiNing 2021/1/6 :11:21
 */
class ObjectViewModel : BaseViewModel() {
    fun insertOrUpdate(id: String, name: String) {
        launchIO {
            val student =
                Student(if (id.isNotEmpty()) id.toLong() else 0, name, 12)
            val list= mutableListOf<Book>()
            for (i in 1..5) {
                var book=Book(name = "书$name$i")
                list.add(book)
            }
            student.books=list
            ObjectBox.boxStore.boxFor(Student::class.java).put(student)
        }
    }

    fun deleteTest(s: Student) {
        launchIO {
            ObjectBox.boxStore.boxFor(Student::class.java).remove(s)
        }
    }
}