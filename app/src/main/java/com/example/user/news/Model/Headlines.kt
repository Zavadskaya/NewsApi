package com.example.user.news.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey

open class Headlines() :RealmObject() {
    @PrimaryKey
    @SerializedName("id")
    var id:String = "1"
    @SerializedName("status")
    lateinit var status:String
    @SerializedName("articles")
    lateinit var articles: RealmList<Article>
    constructor(articles: RealmList<Article>) : this() {
        this.articles = articles
    }

}