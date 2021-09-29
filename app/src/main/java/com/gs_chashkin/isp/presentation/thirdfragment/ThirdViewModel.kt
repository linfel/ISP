package com.gs_chashkin.isp.presentation.thirdfragment

import androidx.lifecycle.ViewModel
import com.example.core.SomeClass
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThirdViewModel
@Inject
constructor(
    private val someClass: SomeClass
) : ViewModel() {
    val number = someClass.doAThing()
//    val number = "do a thing"
}