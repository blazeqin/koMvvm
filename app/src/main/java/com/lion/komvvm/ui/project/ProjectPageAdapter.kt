package com.lion.komvvm.ui.project

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lion.komvvm.entity.NavTypeBean
/**
 * all use the class FragmentStateAdapter
 */
class ProjectPageAdapter(fragment:Fragment): FragmentStateAdapter(fragment) {

    private var tabFragmentCreators: MutableMap<Int, ()->Fragment> = mutableMapOf()

    fun createTabFragment(tabs: List<NavTypeBean>) {
        for (i in tabs.indices) {
            tabFragmentCreators[i] = {CommonSubFragment.instance(tabs[i].id)}
        }
    }

    override fun getItemCount() = tabFragmentCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}