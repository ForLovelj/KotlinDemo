package com.alex.kotlindemo

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    val PI = 3.14

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testSum() {
        val sum = sum(5, 4)
        println("tsum: a + b = $sum")
        println("max: max = ${max(5,4)}")
        testStringNull(null)
    }

    fun sum(a: Int, b: Int) = a + b

    fun max(a: Int, b: Int) = if (a > b) a else b

    fun testStringNull(str: String?){
//        var str: String? = "abc"
//        str = null

//        if (str == null) {
//            println("str ${str}")
//        } else {
//            println("str.length + ${str.length}")
//        }

        println("str 不为空打印str.length 为空打印-1  ${str?.length ?: -1}")
    }

    @Test
    fun testList() {
        var list: List<Int?> = Arrays.asList(1, 2, null, 3)
        println("list: ${list}")
        val filterNotNull = list.filterNotNull()
        println("filterNotNull: ${filterNotNull}")
    }

    @Test
    fun testListFor() {
        //操作集合非空值
        var list: List<Int?> = Arrays.asList(1, 2, null, 3)
        for (i in list) {
            i?.let { println("i: ${i}") }
        }
    }

    @Test
    fun testStudent() {
        val student = Student("zhangsan", 15)

        var s1: String = "26314B0596418BB2EE7FA377A99B88EA1EDBFA6E5CB56039BBF4F97B884E51EFFDC759573DE3A0E55BEE7089F3BB874FFAF6C17BBADC297046A744B60BFA1FCEF07FC6CF0B5DB2C68134E2324F00982DF479D42E4AC060D5E49D3372321F6C6EB6E23F2886FF423C3BAD336A7C7D8808C45ECB7B67E970F8"
        var s2: String = "26314B0596418BB2EE7FA377A99B88EA1EDBFA6E5CB56039BBF4F97B884E51EFFDC759573DE3A0E55BEE7089F3BB874FFAF6C17BBADC297046A744B60BFA1FCEF07FC6CF0B5DB2C68134E2324F00982DF479D42E4AC060D5E49D3372321F6C6EB6E23F2886FF423C3BAD336A7C7D8808C45ECB7B67E970F8"
        println("${s1.equals(s2)}")

    }
}
