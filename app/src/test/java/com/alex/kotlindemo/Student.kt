package com.alex.kotlindemo

/**
 * Created by dth
 * Des:
 * Date: 2018/12/5.
 */
class Student constructor(override var name: String, override var age: Int) : Person(name, age) {

    constructor(name: String) : this(name,0)

    constructor(age: Int) : this("",age)

    init {
        /*初始化块中的代码实际上会成为主构造函数的一部分。委托给主构造函数会作为次构造函数的第一条语句，
        因此所有初始化块中的代码都会在次构造函数体之前执行。即使该类没有主构造函数，这种委托仍会隐式发生，
        并且仍会执行初始化块：*/
        println("init--------age: ${age}  name: ${name}")
    }
}
