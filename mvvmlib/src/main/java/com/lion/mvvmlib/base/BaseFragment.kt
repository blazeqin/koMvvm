package com.lion.mvvmlib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.blankj.utilcode.util.ToastUtils
import com.lion.mvvmlib.R
import com.lion.mvvmlib.util.EventType
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VM : BaseVM, DB : ViewDataBinding> : Fragment() {
    private var isFirst: Boolean = true
    lateinit var mViewModel:VM
    var mBinding:DB? = null
    var mDialog:MaterialDialog? = null

    /**
     * 绑定视图
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val cls =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<*>
        if (ViewDataBinding::class.java != cls && ViewDataBinding::class.java.isAssignableFrom(cls)) {
            mBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
            return mBinding?.root
        }
        return inflater.inflate(layoutId(),container,false)
    }

    abstract fun layoutId(): Int

    /**
     * 视图绑定完成的后续处理
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onVisible()
        createViewModel()
        lifecycle.addObserver(mViewModel)
        registerUIEvent()
        initView(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        onVisible()
    }
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            lazyLoadData()
            isFirst = false
        }
    }

    /**
     * 懒加载
     */
    open fun lazyLoadData() {}

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

    private fun registerUIEvent() {
        mViewModel.eventUI.observe(viewLifecycleOwner, Observer { event->
            when (event.type) {
                EventType.LOADING_SHOW -> showLoading()
                EventType.LOADING_DISMISS -> dismissLoading()
                EventType.EVENT_TOAST -> ToastUtils.showShort(event.obj as String)
                EventType.EVENT_MSG -> handleEvent(event)
            }
        })
    }

    open fun handleEvent(event: EventItem?) {  }
    open fun initView(savedInstanceState: Bundle?) {}

    /**
     * 显示自定义的一个loading
     */
    private fun showLoading() {
        if (mDialog == null) {
            mDialog = MaterialDialog(this.context!!)
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