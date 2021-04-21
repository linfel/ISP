package com.gs_chashkin.isp.isp_files

//противоречия при переопределении

interface FirstInterface {

    fun foo()
    fun bar() = println("A bar")

}

interface SecondInterface {



    fun foo(b: Boolean) =
        println("I ${if (b) "got" else "lost"} focus.")

    fun bar() = println("В фокусе я рычу. Р-р-р!")
}

class FirstClass() : FirstInterface, SecondInterface {

    override fun foo(b: Boolean) {
        super.foo(b)
    }

    override fun foo() {
        TODO("Not yet implemented")
    }


    override fun bar() {
        TODO("Not yet implemented")
    }


}

