package com.gs_chashkin.isp.isp_files

fun main(args: Array<String>) {
//    println("Hello World");
//    var classA = A()
//    println(classA.a)
//    println(classA.isEmpty)
//    classA.a = ""
//    println(classA.a)
//    println(classA.isEmpty)
//    classA.a = "NotBiba"
//    println(classA.a)

    ExtendedObjectDelegatingToObject.doSomethingElse()
    ExtendedObjectDelegatingToObject.doStuff()
}

class A {

    var a : String = "Biba"
        set(value) {
            if (value != "Biba") field = value  //using backing field through ''field'' key-word
        }

    val isEmpty : Boolean
        get() = this.a == ""
}