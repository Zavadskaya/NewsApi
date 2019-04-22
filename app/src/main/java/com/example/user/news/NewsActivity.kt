package com.example.user.news

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.Toast
import com.example.user.news.Model.Article
import com.example.user.news.Model.Headlines
import com.example.user.news.ViewHolder.Adapter.ListNewsAdapter
import com.example.user.news.net.GlobalUrl
import com.example.user.news.net.RetrofitClient
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_news.*
import retrofit2.Call
import retrofit2.Response

class NewsActivity : AppCompatActivity() {

    lateinit var dialog: AlertDialog

    val articles = mutableListOf<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.navigation)
        bottomNavigationView.selectedItemId = R.id.menu_item2
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            intent = Intent(this, MainActivity::class.java)
            when (item.itemId) {
                R.id.menu_item1 -> startActivity(intent)
            }
            true
        }

        setSupportActionBar(findViewById(R.id.toolbar))

        recycler_view_news.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ListNewsAdapter(articles).apply { setHasStableIds(true) }
            setHasFixedSize(true)
        }

        dialog = SpotsDialog(this)
        dialog.setTitle("Loading...")
        dialog.setCancelable(false)
        loadWebSiteSource(showLoading = true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.list_menu, menu)
        var menuItem = menu.findItem(R.id.searchMenu)
        var searchView = menuItem.actionView as SearchView
        var searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.length > 2) {
                    loadWebSiteSource(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                loadWebSiteSource(newText)
                return false
            }
        })

        return true
    }

    private fun loadWebSiteSource(key: String = "", showLoading: Boolean = false) {
        if (showLoading) dialog.show()

        RetrofitClient.newsService.articles("us", "business", GlobalUrl.API_KEY, key)
            .enqueue(object : retrofit2.Callback<Headlines> {
                override fun onFailure(call: Call<Headlines>?, t: Throwable?) {
                    dialog.dismiss()
                    Toast.makeText(baseContext, "Error", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(call: Call<Headlines>?, response: Response<Headlines>?) {
                    dialog.dismiss()

                    articles.clear()
                    articles.addAll(response?.body()!!.articles)

                    recycler_view_news.adapter?.notifyDataSetChanged()
                }
            })
    }
}

