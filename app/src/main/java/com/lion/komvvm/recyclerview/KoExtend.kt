package com.lion.komvvm.recyclerview

/**
 * for dsl(domain specific language)
 */
fun <T> koAdapter(init: KoAdapter<T>.()->Unit): KoAdapter<T>{
    val adapter = KoAdapter<T>()
    adapter.init()
    return adapter
}
