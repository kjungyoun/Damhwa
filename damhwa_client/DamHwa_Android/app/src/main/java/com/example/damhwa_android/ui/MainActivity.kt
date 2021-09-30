package com.example.damhwa_android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.damhwa_android.R
import com.example.damhwa_android.custom.LoadingDialogFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val loadingDialogFragment by lazy { LoadingDialogFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        findViewById<BottomNavigationView>(R.id.main_bottom_navigation).setupWithNavController(navController)
    }

    fun checkLoading(loading: Boolean) {
        when (loading) {
            true -> startLoadingSpinner()
            false -> hideLoadingSpinner()
        }
    }

    private fun startLoadingSpinner() {
        if (!loadingDialogFragment.isAdded){
            loadingDialogFragment.show(supportFragmentManager, "loader")
            Log.e("로그", "로딩생겨")
        }
    }

    private fun hideLoadingSpinner() {
        if (loadingDialogFragment.isAdded) {
            loadingDialogFragment.dismissAllowingStateLoss()
            Log.e("로그", "로딩없애")
        }
    }

}