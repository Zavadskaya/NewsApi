package com.example.user.news.Common

import java.util.*

object FilterISO {
    fun getCountry(): Array<out String>? {
        val locale = Locale.getISOCountries()
        return locale
    }
}