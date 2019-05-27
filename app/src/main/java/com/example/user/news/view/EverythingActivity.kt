package com.example.user.news.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

import com.example.user.news.model.Headlines
import com.example.user.news.net.NewsService
import com.example.user.news.view.fragments.EndlessRecyclerOnScrollListener
import com.example.user.news.viewHolder.adapter.ListNewsAdapter
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_everything.*
import retrofit2.Call
import retrofit2.Response

open class  EverythingActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mAdapter: ListNewsAdapter
    lateinit var source: String

    var data: Headlines = Headlines(articles = RealmList())
    lateinit var progress:ProgressBar
    var isLoading = false
    var pagesize = 10
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.user.news.R.layout.activity_everything)

        progress = findViewById<ProgressBar>(com.example.user.news.R.id.progressBar)
        recycler_view_everything.setHasFixedSize(true)
        mAdapter = ListNewsAdapter(articles = data)
        recycler_view_everything.adapter = mAdapter
        layoutManager = LinearLayoutManager(baseContext)
        recycler_view_everything.layoutManager = layoutManager

        recycler_view_everything.addOnScrollListener(object : EndlessRecyclerOnScrollListener(layoutManager) {
            override fun loadMoreItems() {
                if (!isLoading) {
                    loadWebSiteSource(source, ++page, pagesize)
                    mAdapter.notifyDataSetChanged()
                }
            }
        })

        if (intent != null) {
            source = intent.getStringExtra("sources")
            loadWebSiteSource(source,page,pagesize)
        }
    }

    private fun loadWebSiteSource(source: String,page:Int,pageSize:Int) {
        NewsService.instance.everything(sources = source,page =page ,pageSize = pageSize)
            .enqueue(object : retrofit2.Callback<Headlines> {
                override fun onFailure(call: Call<Headlines>?, t: Throwable?) {
                    Toast.makeText(baseContext, "Error", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(call: Call<Headlines>?, response: Response<Headlines>?) {
                    if (response!!.isSuccessful) {
                        val list = response.body()!!.let { it }
                        data.articles.addAll(list.articles)
                        mAdapter.notifyDataSetChanged()
                        progress.visibility = View.GONE
                    } else {
                        when (response.code()) {
                            404 -> {
                                Log.e("error", "Page is not found!")
                            }
                            500 -> {
                                Log.e("error", "Server error!")
                            }
                        }
                    }
                }
            })
    }

}