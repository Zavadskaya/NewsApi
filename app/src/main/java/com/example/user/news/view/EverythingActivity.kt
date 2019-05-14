package com.example.user.news.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.user.news.model.Headlines
import com.example.user.news.net.GlobalUrl
import com.example.user.news.net.RetrofitClient
import com.example.user.news.viewHolder.adapter.ListNewsAdapter
import kotlinx.android.synthetic.main.activity_everything.*
import retrofit2.Call
import retrofit2.Response

open class EverythingActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mAdapter: ListNewsAdapter
    lateinit var source:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.user.news.R.layout.activity_everything)

        recycler_view_everything.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(baseContext)
        recycler_view_everything.layoutManager = layoutManager

        if(intent != null)
        {
            source = intent.getStringExtra("sources")
            loadWebSiteSource(source)

        }

    }

    private fun loadWebSiteSource(source:String) {
        RetrofitClient.newsService.everything(source,1,5, GlobalUrl.API_KEY)
            .enqueue(object : retrofit2.Callback<Headlines> {
                override fun onFailure(call: Call<Headlines>?, t: Throwable?) {
                    Toast.makeText(baseContext, "Error", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(call: Call<Headlines>?, response: Response<Headlines>?) {
                    mAdapter = ListNewsAdapter(baseContext!!, response?.body()!!)
                    mAdapter.notifyDataSetChanged()
                    recycler_view_everything.adapter = mAdapter
                }


            })
    }

}