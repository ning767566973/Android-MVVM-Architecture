package com.cuining.mvvm.ui.test

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelStore
import com.cuining.mvvm.R
import com.example.common.base.BaseFragment
import com.example.common.base.BaseViewModel
import kotlinx.android.synthetic.main.view_model_test1_fragment.*

class ViewModelTest1Fragment : BaseFragment<ViewModelTestViewModel, ViewDataBinding>() {

    companion object {
        const val KEY = "name"
        fun newInstance(name: String) = ViewModelTest1Fragment().apply {
            arguments = Bundle().apply {
                putString(KEY, name)
            }
        }
    }

    override fun layoutId(): Int {
        return R.layout.view_model_test1_fragment
    }

    override fun isShareVM(): Boolean {
        return true
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        viewModel.info.observe(requireActivity(), {
            println("---------->${arguments?.getString(KEY)}:$it")
            tvValue?.text="$it"
        })

        tvName.text = arguments?.getString(KEY)

        btnJump.setOnClickListener {
            viewModel.getInfo()
        }
    }


}