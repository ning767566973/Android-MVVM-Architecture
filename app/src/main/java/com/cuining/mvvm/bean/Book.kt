package com.cuining.mvvm.bean

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * created by CuiNing 2021/1/6 :16:42
 */
@Entity
data class Book(
    @Id(assignable = true)
    var id: Long? = 0,
    var name: String
)
