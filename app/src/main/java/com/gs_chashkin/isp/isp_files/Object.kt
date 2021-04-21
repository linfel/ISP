package com.gs_chashkin.isp.isp_files

interface Iface {
    fun doStuff() {
        println("lol")
    }
}

object BaseObject: Iface {
    override fun doStuff() { }
}

class BaseClass: Iface {
    override fun doStuff() { println("lol") }
}

object ExtendedObjectDelegatingToObject: Iface by BaseObject {
    fun doSomethingElse() {
        doStuff()
    }
}

object ExtendedObjectDelegatingToClass: Iface by BaseClass() {
    fun doSomethingElse() { }
}

class ExtendedClassDelegatingToObject: Iface by BaseObject {
    fun doSomethingElse() { }
}

class ExtendedClassDelegatingToClass: Iface by BaseClass() {
    fun doSomethingElse() { }
}

fun example() {
    BaseObject.doStuff() // can call doStuff from BaseObject
    ExtendedObjectDelegatingToObject.doStuff() // can call doStuff from ExtendedObject
    ExtendedObjectDelegatingToObject.doSomethingElse() // can also call additional methods on ExtendedObject
}