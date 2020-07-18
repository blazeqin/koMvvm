package com.lion.komvvm.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout

/**
 * 在xml里用到： 设置在属性里
 * 两种写法。
 */
@BindingMethods(BindingMethod(type = TabLayout::class, attribute = "items", method = "setTabText"),
BindingMethod(type = TabLayout::class, attribute = "tabItemClick", method = "setOnItemClick"))
object BindingExtend {
    //======================== fragment_project.xml ============================
    @BindingAdapter(value = ["items"], requireAll = false)
    @JvmStatic
    fun setTabText(tabLayout: TabLayout, items: List<String>) {
        items.forEach {
            tabLayout.addTab(tabLayout.newTab().setText(it))
        }
    }

    @BindingAdapter(value = ["tabItemClick"], requireAll = false)
    @JvmStatic
    fun setOnItemClick(tabLayout: TabLayout, listener: TabLayout.OnTabSelectedListener) {
        tabLayout.addOnTabSelectedListener(listener)
    }
}

//======================== item_project_list.xml ============================
@BindingAdapter(value = ["url", "placeholder"], requireAll = false)
fun setImageUrl(imageView: ImageView, url: String, placeholder: Int) {
    Glide.with(imageView.context)
        .load(url)
        .apply(RequestOptions().placeholder(placeholder))
        .into(imageView)

}