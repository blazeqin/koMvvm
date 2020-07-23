package com.lion.komvvm.ui.activity

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.lion.komvvm.R

/**
 * BottomNavigationView has two problem:
 * 1. always load fragment, cannot have the back stack
 * 2. reselect the tab, it will load fragment again.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }

    // solute the system button click back but crash
    override fun onBackPressed() {
        if (navController.navigateUp()) {
            navController.navigate(R.id.navigation_home)
        } else {
            super.onBackPressed()
        }
    }
}