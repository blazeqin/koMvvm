package com.lion.komvvm.recyclerview

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * view holder 的封装
 */
class BaseViewHolder(parent: ViewGroup, resource: Int):RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(resource,parent, false)
)

abstract class ViewHolderCreator<T> {
    //get the resource layout
    abstract fun getResourceId():Int
    //find the type of view
    abstract fun isGivenViewType(data: T?, position:Int):Boolean
    //bind view holder
    abstract fun onBindViewHolder(
        data: T?, items: MutableList<T>, position: Int,holder:ViewHolderCreator<T>
    )

    //findViewById encapsulation
    var mRootView: View? = null
    fun registerItemView(view:View){
        this.mRootView = view
    }
    fun <V : View> findViewById(viewId: Int): V {
        checkRootView()
        return mRootView!!.findViewById(viewId)
    }

    private fun checkRootView() {
        if (mRootView == null) {
            throw NullPointerException("root view cannot be null...")
        }
    }
}

/**
 * ViewHolderCreator 扩展函数
 *  mainly about TextView and ImageView
 */
fun <T> ViewHolderCreator<T>.setText(viewId: Int, text: String?) = apply {
    val textView: TextView = findViewById(viewId)
    textView.text = text
}

fun <T> ViewHolderCreator<T>.setImageResource(viewId: Int, resId: Int) = apply {
    val imageView: ImageView = findViewById(viewId)
    imageView.setImageResource(resId)
}

fun <T> ViewHolderCreator<T>.setImageBitmap(viewId: Int, bm: Bitmap?) = apply {
    val imageView: ImageView = findViewById(viewId)
    imageView.setImageBitmap(bm)
}

fun <T> ViewHolderCreator<T>.setImageDrawable(viewId: Int, drawable: Drawable?) = apply {
    val imageView: ImageView = findViewById(viewId)
    imageView.setImageDrawable(drawable)
}

fun <T> ViewHolderCreator<T>.setBackground(viewId: Int, drawable: Drawable?) = apply {
    val imageView: ImageView = findViewById(viewId)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        imageView.background = drawable
    } else {
        imageView.setBackgroundDrawable(drawable)
    }
}

fun <T> ViewHolderCreator<T>.setBackgroundResource(viewId: Int, resid: Int) = apply {
    val imageView: ImageView = findViewById(viewId)
    imageView.setBackgroundResource(resid)
}

fun <T> ViewHolderCreator<T>.visible(id: Int) =
    apply { findViewById<View>(id).visibility = View.VISIBLE }

fun <T> ViewHolderCreator<T>.invisible(id: Int) =
    apply { findViewById<View>(id).visibility = View.INVISIBLE }

fun <T> ViewHolderCreator<T>.gone(id: Int) = apply { findViewById<View>(id).visibility = View.GONE }

fun <T> ViewHolderCreator<T>.visibility(id: Int, visibility: Int) =
    apply { findViewById<View>(id).visibility = visibility }

fun <T> ViewHolderCreator<T>.setTextColor(id: Int, color: Int) = apply {
    val view: TextView = findViewById(id)
    view.setTextColor(color)
}

fun <T> ViewHolderCreator<T>.setTextSize(id: Int, sp: Int) = apply {
    val view: TextView = findViewById(id)
    view.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp.toFloat())
}

fun <T> ViewHolderCreator<T>.clicked(id: Int, listener: View.OnClickListener?) =
    apply { findViewById<View>(id).setOnClickListener(listener) }

fun <T> ViewHolderCreator<T>.itemClicked(listener: View.OnClickListener?) =
    apply { mRootView?.setOnClickListener(listener) }

fun <T> ViewHolderCreator<T>.longClicked(id: Int, listener: View.OnLongClickListener?) =
    apply { findViewById<View>(id).setOnLongClickListener(listener) }

fun <T> ViewHolderCreator<T>.isEnabled(id: Int, enable: Boolean = true) =
    apply { findViewById<View>(id).isEnabled = enable }

fun <T> ViewHolderCreator<T>.addView(id: Int, vararg views: View?) = apply {
    val viewGroup: ViewGroup = findViewById(id)
    for (view in views) {
        viewGroup.addView(view)
    }
}

fun <T> ViewHolderCreator<T>.addView(id: Int, view: View?, params: ViewGroup.LayoutParams?) =
    apply {
        val viewGroup: ViewGroup = findViewById(id)
        viewGroup.addView(view, params)
    }

fun <T> ViewHolderCreator<T>.removeAllViews(id: Int) = apply {
    val viewGroup: ViewGroup = findViewById(id)
    viewGroup.removeAllViews()
}

fun <T> ViewHolderCreator<T>.removeView(id: Int, view: View?) = apply {
    val viewGroup: ViewGroup = findViewById(id)
    viewGroup.removeView(view)
}
