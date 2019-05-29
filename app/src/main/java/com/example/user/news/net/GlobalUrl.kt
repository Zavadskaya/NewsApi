package com.example.user.news.net

import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*


object GlobalUrl {
    val BASE_URL = "https://newsapi.org/"
    //USE SECOND KEY, if you have an error
    //{"status":"error","code":"rateLimited","message":"You have made too many requests recently. Developer accounts are limited to 500 requests over a 24 hour period (250 requests available every 12 hours). Please upgrade to a paid plan if you need more requests."}
    private val API_KEYS = listOf<String>(
        "99985a3b843a442fae9c23ade594cedf",
        "8a93f1f1c0b641f4bc2353cd49f749c4",
        "b6f18f27cf2849b39cdc92fab708572c"

    )

    fun getApiKey() = API_KEYS.get(2)

    fun DateToTimeFormat(oldstringDate: String): String? {
        val p = PrettyTime(Locale.ENGLISH)
        var isTime: String? = null
        try {
            val sdf = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                Locale.getDefault()
            )
            val date = sdf.parse(oldstringDate)
            isTime = p.format(date)
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return isTime
    }


}


