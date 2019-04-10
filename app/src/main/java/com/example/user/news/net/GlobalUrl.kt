package com.example.user.news.net
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*



object GlobalUrl {
        val BASE_URL = "https://newsapi.org/"
        val API_KEY = "99985a3b843a442fae9c23ade594cedf"

        fun DateToTimeFormat(oldstringDate: String): String? {
                val p= PrettyTime(Locale.ENGLISH)
                var isTime: String? = null
                try {
                        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
                                Locale.getDefault())
                        val date = sdf.parse(oldstringDate)
                        isTime = p.format(date)
                } catch (e: Throwable) {
                        e.printStackTrace()
                }

                return isTime
        }


}


