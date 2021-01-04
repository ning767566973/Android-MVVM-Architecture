package com.cuining.mvvm.ui.test

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.cuining.mvvm.R
import com.cuining.mvvm.bean.TestABean
import com.cuining.mvvm.databinding.ItemRoomTestBinding
import com.cuining.mvvm.room.dao.DbUtils
import com.example.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_room_test.*

class RoomTestActivity : BaseActivity<RoomTestViewModel, ViewDataBinding>() {
    override fun layoutId(): Int {
        return R.layout.activity_room_test
    }

    private val adapter: BaseQuickAdapter<TestABean, BaseDataBindingHolder<ItemRoomTestBinding>> by lazy {
        object :
            BaseQuickAdapter<TestABean, BaseDataBindingHolder<ItemRoomTestBinding>>(R.layout.item_room_test) {
            override fun convert(
                holder: BaseDataBindingHolder<ItemRoomTestBinding>,
                item: TestABean
            ) {
                holder.dataBinding?.let {
                    it.itemTest = item
                    it.executePendingBindings()
                }
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            deleteTest(adapter.data[position] as TestABean)
        }
    }

    private fun deleteTest(testABean: TestABean) {
        var dialog= AlertDialog.Builder(this)
            .setMessage("确定删除${testABean.name}?")
            .setNegativeButton("确定") { dialog, which ->
                dialog.dismiss()
                viewModel.deleteTest(testABean)
            }
            .create()
        dialog.show()
    }

    override fun initData() {
        DbUtils.getTestADao().getAll().observe(this, {
            adapter.setNewInstance(it as MutableList<TestABean>?)
        })
    }


    fun insertOrUpdate(view: View) {
        val id = editId.text.toString().trim()
        val name = editName.text.toString().trim()
        if (name.isEmpty()) return

        viewModel.insertOrUpdate(id,name)

    }

}