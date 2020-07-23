package com.lion.komvvm.utils

import android.util.SparseArray
import androidx.core.util.set
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

//======================== BottomNavigationView ============================
//to solute the same tab click once more but load every time
fun BottomNavigationView.setupWithController(
    navIds: List<Int>,
    fragmentManager: FragmentManager,
    containerId: Int
) {
    //map of tags and init it
    val navIdToTagMap = SparseArray<String>()
    navIds.forEachIndexed { index, id ->
        val fragmentTag = "bottomNavigation#$index"
        val navHostFragment = obtainNavHostFragment(
            fragmentManager,
            fragmentTag,
            id,
            containerId
        )
        val graphId = navHostFragment.navController.graph.id
        navIdToTagMap[graphId] = fragmentTag
    }
    //setup reselected listener
    setupItemReselected(navIdToTagMap, fragmentManager)
}

private fun BottomNavigationView.setupItemReselected(navIdToTagMap: SparseArray<String>, fragmentManager: FragmentManager) {
    setOnNavigationItemReselectedListener { item->
        val newSelectedTag = navIdToTagMap[item.itemId]
        val selectedFragment = fragmentManager.findFragmentByTag(newSelectedTag) as NavHostFragment
        val navController = selectedFragment.navController
        navController.popBackStack(
            navController.graph.startDestination,false
        )
    }
}

private fun obtainNavHostFragment(
    fragmentManager: FragmentManager,
    fragmentTag: String,
    id: Int,
    containerId: Int
): NavHostFragment {
    //exists , just return
    val existFragment = fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?
    existFragment?.let { return it }
    //not exist, create
    val navHostFragment = NavHostFragment.create(id)
    fragmentManager.beginTransaction()
        .add(containerId,navHostFragment,fragmentTag)
        .commitNow()
    return navHostFragment
}
