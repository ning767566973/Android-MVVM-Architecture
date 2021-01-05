package com.cuining.mvvm.ui.test

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.cuining.mvvm.R
import com.example.common.base.BaseActivity
import com.example.common.base.NoViewModel

class DemoActivity : BaseActivity<NoViewModel,ViewDataBinding>() {
    override fun layoutId(): Int {
      return R.layout.activity_demo
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {

    }

}