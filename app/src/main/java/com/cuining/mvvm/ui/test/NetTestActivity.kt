package com.cuining.mvvm.ui.test

import android.os.Bundle
import android.view.View
import com.cuining.mvvm.R
import com.cuining.mvvm.databinding.ActivityNetTestBinding
import com.example.common.base.BaseActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_net_test.*

class NetTestActivity : BaseActivity<NetTestViewModel,ActivityNetTestBinding>() {

    override fun layoutId()=R.layout.activity_net_test

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData() {
        viewModel.oneResult.observe(this,{
            tvResponse.text=Gson().toJson(it)
        })

        viewModel.moreResult.observe(this,{
            tvResponse.text="${tvResponse.text}\n $it"
        })
    }

    fun oneRequest(view: View) {
        tvResponse.text=""
        viewModel.oneRequest()
    }

    fun moreRequest(view: View) {
        tvResponse.text=""
        viewModel.moreRequest()
    }

}