package com.gs_chashkin.isp.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.core.SomeClass
import com.example.core.SomeInterface
import com.example.core.di.Impl1
import com.example.core.di.Impl2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gs_chashkin.isp.R
import com.gs_chashkin.isp.analytics.AnalyticsAdapter
import com.gs_chashkin.isp.di.AnalyticsModuleTwo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var analytics: AnalyticsAdapter
    @Inject lateinit var randomString: String
    var analyticsTwo = AnalyticsModuleTwo.provideAnalyticsService()
    @Inject
    lateinit var someClass: SomeClass
    @Impl1
    @Inject
    lateinit var someInterfaceImpl: SomeInterface
    @Impl2
    @Inject
    lateinit var someInterfaceImpl2: SomeInterface
//    @Inject
//    lateinit var gson: Gson


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        val navController = findNavController(R.id.fragment_container)

        bottomNavigationView.setupWithNavController(navController)

        Log.d("TAG", randomString)
        Log.d("Tag", someClass.doAThing())
        Log.d("Tag", someClass.doAnotherThing())
        Log.d("Tag", someInterfaceImpl.doInterfaceThing())
        Log.d("Tag", someInterfaceImpl2.doInterfaceThing())




    }
}
