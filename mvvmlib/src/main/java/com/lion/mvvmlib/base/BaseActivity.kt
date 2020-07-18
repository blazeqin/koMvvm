package com.lion.mvvmlib.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.blankj.utilcode.util.ToastUtils
import com.lion.mvvmlib.R
import com.lion.mvvmlib.util.EventType
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VM : BaseVM, DB : ViewDataBinding> : AppCompatActivity() {

    lateinit var mViewModel: VM
    var mBinding:DB? = null
    private var mDialog: MaterialDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewDataBinding()
        registerUIEvent()
        initView(savedInstanceState)
        initData()
    }

    /**
     * 绑定view视图
     */
    private fun initViewDataBinding() {
        val cls =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<*>
        //viewDataBinding 是cls的父类时，绑定视图
        if (ViewDataBinding::class.java != cls && ViewDataBinding::class.java.isAssignableFrom(cls)) {
            mBinding = DataBindingUtil.setContentView(this, layoutId())
            mBinding?.lifecycleOwner = this
        }else
            setContentView(layoutId())
        createViewModel()
    }

    /**
     * 处理UI相关的事件
     */
    private fun registerUIEvent() {
        mViewModel.eventUI.observe(this, Observer { event->
            when (event.type) {
                EventType.LOADING_SHOW -> showLoading()
                EventType.LOADING_DISMISS -> dismissLoading()
                EventType.EVENT_TOAST -> ToastUtils.showShort(event.obj as String)
                EventType.EVENT_MSG -> handleEvent(event)
            }
        })
    }

    /**
     * 创建viewmodel
     */
    private fun createViewModel() {
        val cls = javaClass.genericSuperclass
        if (cls is ParameterizedType) {
            val type = cls.actualTypeArguments[0]
            val tClass = type as? Class<VM> ?: BaseVM::class.java
            mViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(tClass) as VM
        }
    }

    abstract fun layoutId():Int
    open fun initView(savedInstanceState: Bundle?){}
    abstract fun initData()

    open fun handleEvent(event: EventItem) {}

    /**
     * 显示自定义的一个loading
     */
    private fun showLoading() {
        if (mDialog == null) {
            mDialog = MaterialDialog(this)
                .cancelable(false)
                .cornerRadius(8f)
                .customView(R.layout.custom_progress_dialog_view)
                .lifecycleOwner(this)
                .maxWidth(R.dimen.dialog_width)
        }
        mDialog?.show()
    }

    private fun dismissLoading() {
        if (mDialog?.isShowing ?: false) {
            mDialog?.dismiss()
        }
    }
}