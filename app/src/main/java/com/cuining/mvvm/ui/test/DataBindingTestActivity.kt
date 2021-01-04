package com.cuining.mvvm.ui.test

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.cuining.mvvm.R
import com.cuining.mvvm.bean.ArticlesBean
import com.cuining.mvvm.databinding.ActivityDataBindingTestBinding
import com.example.common.base.BaseActivity
import com.example.common.base.NoViewModel
import java.util.*

class DataBindingTestActivity : BaseActivity<DataBindingViewModel,ActivityDataBindingTestBinding>() {
    override fun layoutId()=R.layout.activity_data_binding_test

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        viewModel.result.observe(this,{
            mBinding?.article= ArticlesBean(author = it)
        })

    }

    fun login(view: View) {
       viewModel.request(UUID.randomUUID().toString())
    }

}