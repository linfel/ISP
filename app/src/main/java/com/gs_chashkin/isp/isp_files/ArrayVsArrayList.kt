package com.gs_chashkin.isp.isp_files

import java.util.*
import java.util.concurrent.atomic.AtomicReference
import kotlin.collections.ArrayList

val size = 999_999

fun createArray() : Array<Int> {
    val startTime = Date().time
    val array = Array<Int>(size){it}
    val endTime = Date().time
    println("${endTime - startTime} - time to create Array")
    return array
}

fun createIntArray() : IntArray {
    val startTime = Date().time
    val array = IntArray(size) {0}
    val endTime = Date().time
    println("${endTime - startTime} - time to create IntArray")
    return array
}

fun createArrayList() : ArrayList<Int> {
    val startTime = Date().time
    val arrayList = ArrayList<Int>(size)
    val endTime = Date().time
    println("${endTime - startTime} - time to create ArrayList")
    return arrayList
}

fun setMyArray(array : Array<Int>) {
    val startTime = Date().time
    for (i in array.indices) {
        array[i] = i
    }
    val endTime = Date().time
    println("${endTime - startTime} - time to set Array")
}

fun setMyIntArray(array : IntArray) {
    val startTime = Date().time
    for (i in array.indices) {
        array[i] = i
    }
    val endTime = Date().time
    println("${endTime - startTime} - time to set IntArray")
}


val atomicRefernce = AtomicReference<Any?>()

fun setMyArrayList(array : ArrayList<Int>) {
    val startTime = Date().time
    for (i in 0 until size) {
        array.add(i)
    }
    val endTime = Date().time
    atomicRefernce.set(array)
    println("${endTime - startTime} - time to set ArrayList")
}

fun getFromArray(array: Array<Int>) {
    var tmp = 0
    val startTime = Date().time
    for (i in array.indices) {
        tmp = array[i]
    }
    val endTime = Date().time
    println("${endTime - startTime} - time to get from Array")
}

fun getFromIntArray(array: IntArray) {
    var tmp = 0
    val startTime = Date().time
    for (i in array.indices) {
        tmp = array[i]
    }
    val endTime = Date().time
    println("${endTime - startTime} - time to get from IntArray")
}

fun getFromArrayList(array: ArrayList<Int>) {
    var tmp = 0
    val startTime = Date().time
    for (i in array.indices) {
        tmp = array[i]
    }
    val endTime = Date().time
    println("${endTime - startTime} - time to get from ArrayList")
}


fun mainT(args : Array<String>) {
    val array = createArray()
    for (i in 0..25) {
        var newArray = createArray()
    }
////    val intArray = createIntArray()
//    val arrayList = createArrayList()
//    for (i in 0..25) {
//        var arrayList = createArrayList()
//        Thread.sleep(1000)
//    }
//
//    setMyArray(array)
////    setMyIntArray(intArray)
//    setMyArrayList(arrayList)

//    array.forEach {
//        println("$it - this is array")
//    }
//    arrayList.forEach {
//        println("$it - this is arrayList")
//    }
//
//    getFromArray(array)
//    getFromIntArray(intArray)
//    getFromArrayList(arrayList)


}