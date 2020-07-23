package com.lion.komvvm.recyclerview

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter的封装
 * 还没有完成的任务： 加载更多； 点击事件
 */
class KoAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //data source
    var mDatas:MutableList<T> = mutableListOf()
    // kinds of holders
    private val mTypeHolders = SparseArrayCompat<ViewHolderCreator<T>>()
    //item click listener
    private var mItemClick: ((KoAdapter<T>,Int)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = getHolderByViewType(viewType) ?: throwException(viewType)
        val viewHolder = BaseViewHolder(parent, holder.getResourceId())
        bindClickListener(viewHolder)
        return viewHolder
    }

    fun setItemClickListener(listener: (KoAdapter<T>,Int)->Unit) {
        mItemClick = listener
    }

    private fun bindClickListener(viewHolder: BaseViewHolder) {
        viewHolder.itemView.setOnClickListener {
            //ignore the head view, the layoutPosition maybe wrong.
            mItemClick?.invoke(this, viewHolder.layoutPosition)
        }
    }

    private fun getHolderByViewType(viewType: Int): ViewHolderCreator<T>? {
        return mTypeHolders.get(viewType)
    }

    override fun getItemCount(): Int  = mDatas.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindViewHolder(holder, position, mutableListOf())
    }

    //why have two onBindViewHolder methods???
    //this three parameters' method is not common.
    //we can also implement one method-- the two parameters' method
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        //deliver it to holder, i.e. who uses it, who implements it
        val creatorHolder =
            getHolderByViewType(holder.itemViewType) ?: throwException(holder.itemViewType)
        creatorHolder.registerItemView(holder.itemView)
//        creatorHolder.onBindViewHolder(mDatas[position], mDatas, position, creatorHolder)
        creatorHolder.mFuncForBindViewHolder?.invoke(mDatas[position], position, creatorHolder)
    }

    //get view type: iterator to get it
    override fun getItemViewType(position: Int): Int {
        val data = mDatas[position]
        for (i in 0 until mTypeHolders.size()) {
            val holder = mTypeHolders.valueAt(i)
            if (holder.mFuncForViewType.invoke(data, position)) {
                return mTypeHolders.keyAt(i)
            }
        }
        throw NullPointerException("no holder matches in the position=$position at data source")
    }

    //add data to mTypeHolders' collection
    fun register(holderCreator: ViewHolderCreator<T>) = apply {
        //viewType is defined by yourself. just make sure it doesn't be the same.
        var viewType = mTypeHolders.size()
        while (mTypeHolders.get(viewType) != null) {
            viewType++
        }
        mTypeHolders.put(viewType, holderCreator)
    }

    //add data
    fun addData(position: Int, data: T) {
        mDatas.add(position, data)
        notifyItemInserted(position)
    }

    fun addNewAll(items: MutableList<T>) {
        mDatas = items
        notifyDataSetChanged()
    }

    fun addAll(items: MutableList<T>) {
        mDatas.addAll(items)
        notifyDataSetChanged()
    }

    //remove data
    fun removeData(position: Int) {
        mDatas.removeAt(position)
        notifyItemRemoved(position)
    }

    //update data
    fun updateData(position: Int, data: T, payload:Boolean = true) {
        mDatas.set(position, data)
        if (payload) {
            notifyItemChanged(position,data)
        }else
            notifyItemChanged(position)
    }
    //get item
    fun getItem(position: Int) = mDatas.get(position)
    //attach to recyclerview;
    //use apply, so that we can continue to call other methods in this class
    fun attach(rv:RecyclerView) = apply { rv.adapter = this }

    //submit data
    fun submitData(items: MutableList<T>) {
        mDatas.clear()
        mDatas.addAll(items)
        notifyDataSetChanged()
    }

    private fun throwException(value: Int):ViewHolderCreator<T> {
        throw NullPointerException("No holder available for this view type: $value")
    }

}

fun <T> koAdapter(init: KoAdapter<T>.()->Unit): KoAdapter<T> =
    KoAdapter<T>().apply(init)

fun <T> KoAdapter<T>.addItemView(resourceId: Int, init: ViewHolderCreator<T>.() -> Unit) {
    val holder = ViewHolderCreator<T>(resourceId)
    holder.init()
    register(holder)//保存holder到缓存
}