package com.lion.mvvmlib.base

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.lion.mvvmlib.network.ExceptionHandle
import com.lion.mvvmlib.network.ResponseThrowable
import com.lion.mvvmlib.util.EventType
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class BaseVM : AndroidViewModel(Utils.getApp()), LifecycleObserver {

    /**
     * 统一的UI处理事件：使用single防止多次刷新
     */
    val eventUI by lazy { SingleLiveEvent<EventItem>() }

    /**
     * 方法都在viewscope里启动，当相关view销毁时会自动取消协程任务
     */
    fun launchUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    /**
     * 用flow的方式调用方法
     * 链式调用
     */
    fun <T> launchFlow(block: suspend () -> T): Flow<T> {
        return flow {
            val t = block()
            emit(t)
        }
    }

    /**
     *  不过滤请求结果
     * @param block 请求体
     * @param error 失败回调
     * @param complete  完成回调（无论成功失败都会调用）
     * @param isShowLoading 是否显示加载框
     */
    fun launchNoFilter(
        block: suspend CoroutineScope.() -> Unit,
        error: suspend CoroutineScope.(ResponseThrowable) -> Unit = {},
        complete: suspend CoroutineScope.() -> Unit = {},
        isShowLoading: Boolean = true
    ) {
        if (isShowLoading) eventUI.value = EventItem(EventType.LOADING_SHOW)
        launchUI {
            handleExceptionNoFilter(
                {withContext(Dispatchers.IO){block()}},
                { error(it)},
                {
                    eventUI.value = EventItem(EventType.LOADING_DISMISS)
                    complete()
                }
            )
        }
    }

    /**
     * 过滤请求结果: almost all requests use this method
     * @param block 请求体
     * @param success 成功回调
     * @param errorCall 失败回调
     * @param complete  完成回调（无论成功失败都会调用）
     * @param isShowLoading 是否显示加载框
     */
    fun <T> launchFilterResponse(
        block: suspend CoroutineScope.() -> BaseResult<T>,
        success:(T) -> Unit,
        error:(ResponseThrowable)->Unit = {},
        complete:()->Unit = {},
        isShowLoading: Boolean = true
    ){
        if (isShowLoading) eventUI.value = EventItem(EventType.LOADING_SHOW)
        launchUI {
            handleExceptionFilterResult(
                { withContext(Dispatchers.IO){block()} },
                {response->
                    filterResponse(response){success(it)}
                },
                {error(it)},
                {
                    eventUI.setValue(EventItem(EventType.LOADING_DISMISS))
                    complete()
                }
            )
        }
    }
    /**
     * 过滤response结果
     */
    private suspend fun <T> filterResponse(
        response: BaseResult<T>,
        success: suspend CoroutineScope.(T) -> Unit
    ) {
        coroutineScope {
            LogUtils.i("get data: ${response.errCode}")
            if (response.isSuccess())
                success(response.data)
            else
                throw ResponseThrowable(response.errCode, response.errMsg)
        }
    }

    /**
     * 异常结果处理：过滤结果
     */
    private suspend fun <T> handleExceptionFilterResult(
        block: suspend CoroutineScope.() -> BaseResult<T>,
        success: suspend CoroutineScope.(BaseResult<T>) -> Unit,
        error: suspend CoroutineScope.(ResponseThrowable) -> Unit,
        complete: suspend CoroutineScope.() -> Unit,
        isHandleError: Boolean = true
    ) {
        coroutineScope {
            try {
                success(block())
            } catch (e: Throwable) {
                val err = ExceptionHandle.handleException(e)
                if (isHandleError) error(err)
                else eventUI.value = EventItem(EventType.EVENT_TOAST, "${err.code}:${err.msg}")
            } finally {
                complete()
            }
        }
    }

    /**
     * 异常结果处理：没有过滤
     */
    private suspend fun handleExceptionNoFilter(
        block: suspend CoroutineScope.() -> Unit,
        error: suspend CoroutineScope.(ResponseThrowable) -> Unit,
        complete: suspend CoroutineScope.() -> Unit,
        isHandleError: Boolean = true
    ) {
        coroutineScope {
            try {
                block()
            } catch (e: Throwable) {
                val err = ExceptionHandle.handleException(e)
                if (isHandleError) error(err)
                else eventUI.value = EventItem(EventType.EVENT_TOAST, "${err.code}:${err.msg}")
            } finally {
                complete()
            }
        }
    }
}