package com.example.user.news.commons

import com.example.user.news.model.Article
import com.example.user.news.model.Source
import com.example.user.news.model.Sources
import com.example.user.news.net.NewsService
import java.util.*

object FilterISO {
    fun getCountry(): Array<String> {
        val locale = Locale.getISOCountries()
        return locale
    }
}