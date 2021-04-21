package com.gs_chashkin.isp.isp_files

import kotlin.reflect.KProperty

fun main(args: Array<String>) {
    var a = 2
    println(someMethod(a, {println("Wake the fuck up samurai")}))
}
fun someMethod(a: Int, func: () -> Unit):Int {
    func()
    return 2*a
}

interface InterfaceOne {
    fun run()
}

class NotInline(val value: String) {

    val user : User by Userdelegates()


}


inline class Inline(val value: String) : InterfaceOne {
    override fun run() {
        TODO("Not yet implemented")
    }
}


class User

class Userdelegates {
    operator fun getValue(ref : NotInline, property : KProperty<*>) : User {
        return User()
    }

}
