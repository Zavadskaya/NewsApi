package com.example.user.news.commons

import java.util.*

object FilterISO {
    fun getCountry(): Array<String> {
        val locale = Locale.getISOCountries()
        return locale
    }
}