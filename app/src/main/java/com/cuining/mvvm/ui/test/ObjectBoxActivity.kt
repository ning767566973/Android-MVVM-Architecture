package com.cuining.mvvm.ui.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.cuining.mvvm.R
import com.cuining.mvvm.bean.Book
import com.cuining.mvvm.bean.Student
import com.cuining.mvvm.bean.Student_
import com.cuining.mvvm.bean.TestABean
import com.cuining.mvvm.databinding.ItemObjectboxTestBinding
import com.cuining.mvvm.databinding.ItemRoomTestBinding
import com.cuining.mvvm.objectbox.ObjectBox
import com.example.common.base.BaseActivity
import com.example.common.base.NoViewModel
import io.objectbox.android.AndroidScheduler
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.reactive.DataObserver
import kotlinx.android.synthetic.main.activity_object_box.*
import kotlinx.android.synthetic.main.activity_object_box.editId
import kotlinx.android.synthetic.main.activity_object_box.editName
import kotlinx.android.synthetic.main.activity_object_box.recyclerView
import kotlinx.android.synthetic.main.activity_room_test.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ObjectBoxActivity : BaseActivity<ObjectViewModel, ViewDataBinding>() {

    override fun layoutId(): Int {
        return R.layout.activity_object_box
    }

    private val adapter: BaseQuickAdapter<Student, BaseDataBindingHolder<ItemObjectboxTestBinding>> by lazy {
        object :
            BaseQuickAdapter<Student, BaseDataBindingHolder<ItemObjectboxTestBinding>>(R.layout.item_objectbox_test) {
            override fun convert(
                holder: BaseDataBindingHolder<ItemObjectboxTestBinding>,
                item: Student
            ) {
                holder.dataBinding?.let {
                    it.student = item
                    it.executePendingBindings()
                }
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            deleteTest(adapter.data[position] as Student)
        }
    }


    private fun deleteTest(testABean: Student) {
        var dialog = AlertDialog.Builder(this)
            .setMessage("确定删除${testABean.name}?")
            .setNegativeButton("确定") { dialog, which ->
                dialog.dismiss()
                viewModel.deleteTest(testABean)
            }
            .create()
        dialog.show()
    }


    override fun initData() {
        //利用liveData监听变化
        val build = ObjectBox.boxStore.boxFor(Student::class.java)
            .query()
            .build()
        ObjectBoxLiveData(build).observe(this, {
            adapter.setNewInstance(it)
        })

//        ObjectBox.boxStore.boxFor(Student::class.java).removeAll()
//        ObjectBox.boxStore.boxFor(Book::class.java).removeAll()

//        ObjectBox.boxStore.boxFor(Student::class.java)
//            .query()
//            .build()
//            .subscribe()
////            .single()
//            .on(AndroidScheduler.mainThread())
//            .observer { adapter.setNewInstance(it) }

    }

    fun insertOrUpdate(view: View) {
        val id = editId.text.toString().trim()
        val name = editName.text.toString().trim()
        if (name.isEmpty()) return
        viewModel.insertOrUpdate(id, name)
    }

}