package com.silverytitan.mvvmframe.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ClickUtils
import com.silverytitan.mvvmframe.utils.ClassUtil

abstract class BaseMVVMFragment<VB : ViewDataBinding, VM : BaseViewModel>(@LayoutRes var layoutId: Int) :
    Fragment(), IBaseView, View.OnClickListener {
    private var _bind: VB? = null
    val bind: VB
        get() = _bind!!
    private var _viewModel: VM? = null
    val viewModel: VM
        get() = _viewModel!!
    private var mIsHidden = false //fragment为不可见并且fragment初次创建时onStart方法执行网络请求或加载数据库所需menu
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bind = DataBindingUtil.inflate(inflater, layoutId, container, false)
        bind.lifecycleOwner = this
        initViewModel()
        initObserver()
        return bind.root
    }

    override fun onHiddenChanged(hidden: Boolean) {
        mIsHidden = hidden
        if (!hidden) {
            updateData()
        }
        super.onHiddenChanged(hidden)
    }

    override fun onStart() {
        super.onStart()
        if (!mIsHidden) {
            updateData()
        }
    }

    open fun updateData() {}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
    }

    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
        val viewModelClass = ClassUtil.getViewModel<VM>(this)
        if (viewModelClass != null) {
            _viewModel = ViewModelProvider(this).get(viewModelClass)
        }
    }

    override fun onClick(v: View?) {
        onDebouncingClick(v)
    }

    /** 消除抖动点击事件，子类绑定点击事件需要重写 */
    open fun onDebouncingClick(v: View?) {}

    /**
     * 绑定控件点击事件，防止重复点击
     */
    fun applyDebouncingClickListener(vararg views: View) {
        ClickUtils.applyGlobalDebouncing(views, this)
        for (v in views) {
            ClickUtils.applyPressedBgDark(v)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mIsHidden = false //置为false 确保下次打开执行onstart
    }
}