package com.lion.komvvm.recyclerview

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView的相关封装
 * for dsl(domain specific language)
 */
class RecyclerSetup<T>(private val rv: RecyclerView){
    lateinit var mDatas: MutableList<T>
    var mAdapter: KoAdapter<T>? = null

    fun dataSource(items: MutableList<T>?) = apply {
        mDatas = items ?: mutableListOf()
    }

    fun withLayoutManager(init: RecyclerSetup<T>.() -> RecyclerView.LayoutManager) = apply {
        rv.layoutManager = init()
    }

    fun adapter(init: KoAdapter<T>.() -> Unit) = apply {
        mAdapter = koAdapter(init)
    }

    //attach to recyclerview;
    //use apply, so that we can continue to call other methods in this class
    fun attach() = apply {
        rv.adapter = mAdapter
        mAdapter?.submitData(mDatas)
    }

    fun addItemDecoration(decorate: RecyclerView.ItemDecoration) = apply {
        rv.addItemDecoration(decorate)
    }
}

/**
 * recyclerView 的扩展函数
 */
fun <T> RecyclerView.setup(block:RecyclerSetup<T>.()->Unit): RecyclerSetup<T>{
    val rvSetup = RecyclerSetup<T>(this).apply(block)
    if (layoutManager == null) {
        layoutManager = LinearLayoutManager(context)
    }
    return rvSetup
}

//======================== 数据源的处理 ============================
fun <T> RecyclerView.addData(position: Int, data: T) {
    if (adapter is KoAdapter<*>) {
        (adapter as KoAdapter<T>).addData(position, data)
    }
}

fun RecyclerView.removeData(position: Int) {
    if (adapter is KoAdapter<*>) {
        (adapter as KoAdapter<*>).removeData(position)
    }
}

fun <T> RecyclerView.updateData(position: Int, data: T, payload: Boolean = true) {
    if (adapter is KoAdapter<*>) {
        (adapter as KoAdapter<T>).updateData(position,data, payload)
    }
}

fun <T> RecyclerView.submitData(items: MutableList<T>) {
    if (adapter is KoAdapter<*>) {
        (adapter as KoAdapter<T>).submitData(items)
    }
}

fun <T> RecyclerView.getItem(position: Int): T? {
    return if (adapter is KoAdapter<*>) {
        (adapter as KoAdapter<T>).getItem(position)
    }else null
}