package com.example.core

import javax.inject.Inject

class WorkerSomeClass
@Inject constructor(){

    fun doAThing(): String {
        return "Look i did a Thing for Worker"
    }

}