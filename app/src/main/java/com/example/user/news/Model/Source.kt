package com.example.user.news.model

import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class Source:RealmModel  {

    var id: String? = null
    var name: String? = null
    var description: String? = null
    var url: String? = null
    var category: String? = null
    var language: String? = null
    var country: String? = null


}