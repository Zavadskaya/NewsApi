package com.example.user.news.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Article :RealmObject{
    constructor()

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


    var category: String? = null
    var country: String? = null
}