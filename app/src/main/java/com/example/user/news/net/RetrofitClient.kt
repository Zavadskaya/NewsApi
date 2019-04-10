package com.example.user.news.net

import com.example.user.news.Interface.NewsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var retrofit:Retrofit?=null
        val newsService: NewsService
        get()= getClient(GlobalUrl.BASE_URL).create(NewsService::class.java)

    fun getClient(baseUrl:String?):Retrofit{
        if(retrofit == null)
        {
            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl!!)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit!!
    }




}