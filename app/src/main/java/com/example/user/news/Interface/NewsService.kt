package com.example.user.news.`interface`
import com.example.user.news.model.Headlines
import com.example.user.news.model.Sources
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("v2/sources")
    fun sources(
        @Query("country") sources: String,
        @Query("category") category: String,
        @Query("apiKey")apiKey: String):
            Call<Sources>


    @GET("v2/top-headlines")
    fun articles(@Query("country") country: String,
                 @Query("category") category: String,
                 @Query("apiKey") apiKey: String,
                 @Query("q") keyword:String):
            Call<Headlines>

    @GET("v2/everything")
    fun everything(@Query("sources") sources: String,
                   @Query("page") page:Int,
                   @Query("pageSize") pageSize:Int,
                 @Query("apiKey") api: String):
            Call<Headlines>

}