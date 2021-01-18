package com.cuining.mvvm.ui.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import com.cuining.mvvm.R
import com.cuining.mvvm.utils.getArticleService
import com.example.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_flow_test.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class FlowTestActivity : BaseActivity<FlowTestViewModel, ViewDataBinding>() {


    override fun layoutId(): Int {
        return R.layout.activity_flow_test
    }

    override fun initView(savedInstanceState: Bundle?) {

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                viewModel.search.value = s.toString()
            }
        })

    }

    override fun initData() {
        viewModel.search.asFlow()
            .debounce(1000)
            .asLiveData()
            .observe(this, Observer {
                println("------------->$it")
            })

        var list = mutableListOf<Int>()
        for (i in 1..10) {
            list.add(i)
        }

        var list2 = mutableListOf<Int>()
        for (i in 10..20) {
            list2.add(i)
        }

        var list3 = mutableListOf<Int>()
        for (i in 20..30) {
            list3.add(i)
        }
//        lifecycleScope.launch {
//            list.asFlow()
//                .map {
//                    println("---------->map:${Thread.currentThread()}:$it")
//                    "哈哈$it"
//                }
//                .flowOn(Dispatchers.Main)
//                .collect {
//                    println("---------->${Thread.currentThread()}:$it")
//                }
//        }
//

        lifecycleScope.launch {
            val toList = flowOf(list, list2, list3)
                .flatMapConcat {
                    flow {
                        it.forEach {
                            emit(it)
                        }
                    }
                }.toList()

            toList.forEach{
                println("--------------->$it")
            }
        }


        list.asFlow()
//            .map {
//                "哈哈$it"
//            }
//            .asLiveData()
//            .observe(this, Observer {
//                println("------------->$it")
//            })

    }
}