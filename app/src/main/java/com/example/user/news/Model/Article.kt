package com.example.user.news.model

class Article {
    val id:String?=null
    var source: Source?=null
    var author: String?=null
    var title: String?=null
    var description: String?=null
    var url: String?=null
    var urlToImage: String?=null
    var publishedAt: String?=null
    lateinit var sours: List<Source>

}