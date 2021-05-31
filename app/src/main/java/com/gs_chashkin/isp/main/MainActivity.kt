package com.gs_chashkin.isp.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gs_chashkin.isp.AnalyticsAdapter
import com.gs_chashkin.isp.AnalyticsModuleTwo
import com.gs_chashkin.isp.IspApplication
import com.gs_chashkin.isp.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var analytics: AnalyticsAdapter
    var analyticsTwo = AnalyticsModuleTwo.provideAnalyticsService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appContainer = (application as IspApplication).appContainer

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        val navController = findNavController(R.id.fragment_container)

        bottomNavigationView.setupWithNavController(navController)
    }
}
