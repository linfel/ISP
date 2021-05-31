package com.gs_chashkin.isp.presentation.thirdfragment

import androidx.lifecycle.ViewModel
import com.example.core.SomeClass
import javax.inject.Inject

class ThirdViewModel @Inject constructor(someClass: SomeClass) : ViewModel() {
    val number = someClass.doAThing()
}