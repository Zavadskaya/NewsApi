package com.example.user.news.model

class Source  {

    var id: String? = null
    var name: String? = null
    var description: String? = null
    var url: String? = null
    var category: String? = null
    var language: String? = null
    var country: String? = null

    constructor(
        Id: String?,
        Name: String?,
        Description: String?,
        Url: String?,
        Category: String?,
        Language: String?,
        Country: String?
    )
    {
        this.id = Id
        this.name = Name
        this.description = Description
        this.url = Url
        this.category = Category
        this.language = Language
        this.country = Country
    }
}