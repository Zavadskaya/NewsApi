package com.example.user.news.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Article:RealmModel{
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


}