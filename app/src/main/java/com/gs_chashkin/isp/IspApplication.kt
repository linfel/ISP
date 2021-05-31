package com.gs_chashkin.isp

import android.app.Application
import com.gs_chashkin.isp.di.AppContainer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class IspApplication : Application() {

    val appContainer = AppContainer()

}