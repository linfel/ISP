package com.example.core

import javax.inject.Inject

class SomeInterfaceImpl
@Inject
constructor(): SomeInterface {
    override fun doInterfaceThing(): String {
        return "I did SomeInterfaceThing"
    }
}