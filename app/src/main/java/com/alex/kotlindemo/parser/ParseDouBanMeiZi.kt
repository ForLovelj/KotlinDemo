package com.alex.kotlindemo.parser


import com.alex.kotlindemo.model.DouBanMeizi
import org.jsoup.Jsoup
import java.util.*

/**
 * Created by dth
 * Des:
 * Date: 2018/12/12.
 */
class ParseDouBanMeiZi {

    companion object {

        fun JsoupDoubanMeizi(html: String, cid: Int): List<DouBanMeizi> {
            val list = ArrayList<DouBanMeizi>()
            try {

                val parse = Jsoup.parse(html)
                val elements = parse.select("div[class=thumbnail]>div[class=img_single]>a>img")
                var meizi: DouBanMeizi
                for (e in elements) {
                    val src = e.attr("src")
                    val title = e.attr("title")

                    meizi = DouBanMeizi(src, title, cid)
                    list.add(meizi)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return list

        }

    }
}
