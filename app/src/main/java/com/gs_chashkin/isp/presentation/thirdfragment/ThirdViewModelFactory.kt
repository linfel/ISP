package com.gs_chashkin.isp.presentation.thirdfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gs_chashkin.isp.di.AppContainer

class ThirdViewModelFactory(private val appContainer: AppContainer): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThirdViewModel::class.java)) {
            return ThirdViewModel(appContainer) as T
        }
        throw IllegalArgumentException("Unknown Class")
    }
}