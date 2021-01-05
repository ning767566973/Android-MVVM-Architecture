package com.cuining.mvvm.ui.test

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cuining.mvvm.R
import com.example.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_view_model_test.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModelTestActivity : BaseActivity<ViewModelTestViewModel, ViewDataBinding>() {
    override fun layoutId(): Int {
        return R.layout.activity_view_model_test
    }

    private val fragments by lazy {
        arrayListOf(
            ViewModelTest1Fragment.newInstance("fragment1"),
            ViewModelTest1Fragment.newInstance("fragment2"),
            ViewModelTest1Fragment.newInstance("fragment3"),
            ViewModelTest1Fragment.newInstance("fragment4"),
            ViewModelTest1Fragment.newInstance("fragment5"),
            ViewModelTest1Fragment.newInstance("fragment6"),
            ViewModelTest1Fragment.newInstance("fragment7")
        )
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return fragments.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }

        }
//        viewModel.info.observe(this, {
//            btn.text = it
//        })
    }

    override fun initData() {

        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                delay(1000)

            }
            withContext(Dispatchers.Main){

            }


        }
    }

    fun transferData(view: View) {
        viewModel.getInfo()
    }

}