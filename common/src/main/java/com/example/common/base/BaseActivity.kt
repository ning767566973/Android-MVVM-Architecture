package com.example.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.example.common.R
import java.lang.reflect.ParameterizedType

/**
 * @author: cuining
 * @date: 2020/4/17
 */
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var viewModel: VM

    protected var mBinding: DB? = null

    lateinit var mTvTitle: TextView
    lateinit var mToolBar: Toolbar

    override fun onStart() {
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewDataBinding()
        lifecycle.addObserver(viewModel)
        //注册 UI事件
        registorDefUIChange()
        initView(savedInstanceState)
        initData()
    }

    /**
     * 注册 UI 事件
     */
    private fun registorDefUIChange() {
        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })
        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })
        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtils.showShort(it)
        })
    }

    /**
     * DataBinding
     */
    private fun initViewDataBinding() {
        val cls =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<*>
        if (ViewDataBinding::class.java != cls && ViewDataBinding::class.java.isAssignableFrom(cls)) {
            mBinding = DataBindingUtil.setContentView(this, layoutId())
            mBinding?.lifecycleOwner = this
        } else setContentView(getLayout())
        createViewModel()
    }

    private fun getLayout(): View {
        val view = LayoutInflater.from(this).inflate(R.layout.layout_base_activity, null)
        var linearContent = view.findViewById<LinearLayout>(R.id.linearContent)
        mToolBar = view.findViewById<Toolbar>(R.id.toolBar)
        mTvTitle = view.findViewById<TextView>(R.id.tvTitle)
        mToolBar.visibility = if (isShowToolBar()) View.VISIBLE else View.GONE
        setSupportActionBar(mToolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false);
        mToolBar.setNavigationOnClickListener {
            onBackPressed()
        }
        LayoutInflater.from(this).inflate(layoutId(), linearContent, true)
        return view
    }

    /**
     * 创建 ViewModel
     */
    @Suppress("UNCHECKED_CAST")
    private fun createViewModel() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[0]
            val tClass = tp as? Class<VM> ?: BaseViewModel::class.java
            viewModel = ViewModelProvider(this).get(tClass) as VM
        }
    }


    /**
     * 打开等待框
     */
    private fun showLoading() {
    }

    /**
     * 关闭等待框
     */
    private fun dismissLoading() {
    }
    /**
     * 设置title
     * 自动显示Toolbar
     */
    fun setTitle(title: String?) {
        if (mToolBar.visibility == View.GONE) mToolBar.visibility = View.VISIBLE
        mTvTitle.text = title
    }
    //以下代码是toolbar 右上角menu 可以copy到具体activity修改
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.more_menu, menu)
//        return true
//    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.action_more -> {
//
//            }
//        }
//        return true
//    }
    open fun isShowToolBar(): Boolean = false
    abstract fun layoutId(): Int
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun initData()
}

