package com.example.core

import javax.inject.Inject

class SomeClass
@Inject constructor(
    private val otherClass: SomeOtherClass,
)
{
    fun doAThing(): String {
        return "Look i did a Thing"
    }

    fun doAnotherThing(): String {
        return otherClass.doAnotherThing()
    }



}