package com.cuining.mvvm.ui.test

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.ToastUtils
import com.cuining.mvvm.R
import com.cuining.mvvm.bean.ArticlesBean
import com.cuining.mvvm.databinding.ActivityDataBindingTestBinding
import com.example.common.base.BaseActivity
import com.example.common.base.NoViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class DataBindingTestActivity : BaseActivity<DataBindingViewModel,ActivityDataBindingTestBinding>() {
    override fun layoutId()=R.layout.activity_data_binding_test

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        viewModel.result.observe(this, {
            println("数据改变")
//            mBinding?.article = ArticlesBean(author = it)
            mBinding?.article = ArticlesBean(author = it)
        })
//        lifecycleScope.launch {
//            for (i in 1..100) {
//                delay(1000)
//                mBinding?.article = ArticlesBean(author = "${i}s")
//            }
//        }
    }

    fun login(view: View) {
       viewModel.request(UUID.randomUUID().toString())
    }

}