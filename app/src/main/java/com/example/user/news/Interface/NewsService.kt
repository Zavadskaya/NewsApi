package com.example.user.news.Interface
import com.example.user.news.Model.Headlines
import com.example.user.news.Model.Sources
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("v2/sources")
    fun sources(@Query("country") sources: String,
                @Query("category")category: String,
                @Query("apiKey")apiKey: String):
            Call<Sources>

    @GET("v2/top-headlines")
    fun articles(@Query("country") sources: String,
                          @Query("category") pageSize: String,
                          @Query("apiKey") apiKey: String,
                          @Query("q") keyword:String):
    Call<Headlines>


}