package com.example.user.news.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

open class Article :RealmObject{
    @PrimaryKey
    @SerializedName("id")
    lateinit var id:String
    @SerializedName("source")
    var source: Source?=null
    @SerializedName("author")
    var author: String?=null
    @SerializedName("title")
    var title: String?=null
    @SerializedName("description")
    var description: String?=null
    @SerializedName("url")
    var url: String?=null
    @Expose
    @SerializedName("urlToImage")
    var urlToImage: String?=null
    @Expose
    @SerializedName("publishedAt")
    var publishedAt: String?=null
    @SerializedName("articles")
    var articles: RealmList<Article> = RealmList<Article>()
    constructor(articles: RealmList<Article>) : this() {
        this.articles = articles
    }
    constructor()

}