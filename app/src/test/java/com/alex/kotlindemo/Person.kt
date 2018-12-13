package com.alex.kotlindemo

/**
 * Created by dth
 * Des:
 * Date: 2018/12/5.
 */
open class Person(open var name: String, open var age: Int){


    init {
        println("init-------person ${name}  ${age}")
    }
}