package com.example.user.news.view

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.example.user.news.R
import com.example.user.news.model.Sources
import com.example.user.news.net.NewsService
import com.example.user.news.viewHolder.adapter.ListSourceAdapter
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_source.*
import retrofit2.Call
import retrofit2.Response


class SourceActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: ListSourceAdapter
    lateinit var dialog: SpotsDialog
    lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_source)

        recycler_view_source_news.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(baseContext)
        recycler_view_source_news.layoutManager = layoutManager

        bottomNavigationView = this.findViewById(R.id.navigation)
        bottomNavigationView.selectedItemId = R.id.menu_item1
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            intent = Intent(this, MainActivity::class.java)
            val intent2 = Intent(this, SearchActivity::class.java)

            when (item.itemId) {
                R.id.menu_item1 ->
                    startActivity(intent)

                R.id.menu_item2 ->
                    startActivity(intent2)

                R.id.menu_item3 -> baseContext

            }
            true
        }
//        dialog = SpotsDialog(baseContext)
//        dialog.setTitle("Loading...")
//        dialog.setCancelable(false)
        loadWebSiteSource(" ", " ")
//        dialog.dismiss()

    }

    private fun loadWebSiteSource(country: String, category: String) {
        NewsService.instance.sources(sources = country, category = category)
            .enqueue(object : retrofit2.Callback<Sources> {
                override fun onFailure(call: Call<Sources>, t: Throwable) {
                    Toast.makeText(baseContext, "Error", Toast.LENGTH_SHORT).show()
                    Log.e("error", call.toString())
                }

                override fun onResponse(call: Call<Sources>, response: Response<Sources>) {
                    adapter = ListSourceAdapter(baseContext!!, response.body()!!)
                    recycler_view_source_news.adapter = adapter
                    adapter.notifyDataSetChanged()
                    Log.e("Response", response.toString())
                }
            })
    }

}