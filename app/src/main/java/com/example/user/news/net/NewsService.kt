package com.example.user.news.net

import android.util.Log
import com.example.user.news.model.Headlines
import com.example.user.news.model.Sources
import com.example.user.news.net.GlobalUrl.getApiKey
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("v2/sources")
    fun sources(
        @Query("country") sources: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = getApiKey()
    ): Call<Sources>


    @GET("v2/top-headlines")
    fun articles(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = getApiKey(),
        @Query("q") keyword: String
    ): Call<Headlines>

    @GET("v2/everything")
    fun everything(
        @Query("sources") sources: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") api: String = getApiKey()
    ): Call<Headlines>


    companion object {
        val instance: NewsService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(GlobalUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())
                .build()
            retrofit.create(NewsService::class.java)
        }

        private fun provideOkHttpClient() = OkHttpClient.Builder().apply {
            addNetworkInterceptor(
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Log.d("RetrofitClient", it) })
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        }.build()
    }
}