package com.example.user.news.model

import io.realm.RealmObject

class Headlines  {
    private val status: String? = null
    private val source: String? = null
    private val sortBy: String? = null
    lateinit var articles: List<Article>
}

