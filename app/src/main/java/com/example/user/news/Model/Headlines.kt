package com.example.user.news.Model

class Headlines {
    var status: String? = null
    var code: String? = null
    var message: String? = null
    var totalResults: Int? = null
    lateinit var articles: List<Article>
}

