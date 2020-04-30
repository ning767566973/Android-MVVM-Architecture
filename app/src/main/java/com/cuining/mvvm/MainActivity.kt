package com.cuining.mvvm

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.cuining.mvvm.base.BaseActivity
import com.cuining.mvvm.base.NoViewModel

class MainActivity : BaseActivity<NoViewModel, ViewDataBinding>() {
    override fun layoutId() = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {

    }

}
