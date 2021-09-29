package com.example.core

import com.example.core.di.Impl1
import com.example.core.di.Impl2
import javax.inject.Inject

class SomeClass
@Inject constructor(
    private val otherClass: SomeOtherClass,
    @Impl1 private val someInterfaceImpl: SomeInterface,
    @Impl2 private val someInterfaceImpl2: SomeInterface,
)
{

    fun doAThing(): String {
        return "Look i did a Thing"
    }

    fun doAnotherThing(): String {
        return otherClass.doAnotherThing()
    }

    fun doInterfaceThing(): String {
        return "Look i got: ${someInterfaceImpl}"
    }

    fun doInterfaceThing2(): String {
        return "Look i got: ${someInterfaceImpl}"
    }

}