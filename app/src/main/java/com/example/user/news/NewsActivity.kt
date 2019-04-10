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
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.Toast

import com.example.user.news.Interface.NewsService
import com.example.user.news.Model.Headlines
import com.example.user.news.ViewHolder.Adapter.ListNewsAdapter
import com.example.user.news.net.GlobalUrl
import com.example.user.news.net.RetrofitClient
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_news.*
import retrofit2.Call
import retrofit2.Response

class NewsActivity : AppCompatActivity() {


    lateinit var layoutManager: LinearLayoutManager
    lateinit var service: NewsService
    lateinit var adapter: ListNewsAdapter
    lateinit var dialog: AlertDialog


   lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        bottomNavigationView = findViewById(R.id.navigation)
        bottomNavigationView.selectedItemId = R.id.menu_item2
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            intent = Intent(this,MainActivity::class.java)
            when (item.itemId) {
                R.id.menu_item1 -> startActivity(intent)
            }
            true
        }

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        service = RetrofitClient.newsService

        recycler_view_news.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recycler_view_news.layoutManager = layoutManager

        dialog = SpotsDialog(this)
        dialog.show()
        dialog.setTitle("Loading...")
        dialog.setCancelable(false)
        loadWebSiteSource("")
        dialog.dismiss()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.list_menu, menu)
        var menuItem = menu.findItem(R.id.searchMenu)
        var searchView = menuItem.actionView as SearchView
        var searchManager:SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if(query.length >2)
                {
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

    private fun loadWebSiteSource(key:String) {
        service.articles("us","business", GlobalUrl.API_KEY,key).enqueue(object : retrofit2.Callback<Headlines> {
            override fun onFailure(call: Call<Headlines>?, t: Throwable?) {
                Toast.makeText(baseContext, "Error", Toast.LENGTH_SHORT)
                        .show()
            }

            override fun onResponse(call: Call<Headlines>?, response: Response<Headlines>?) {
                adapter = ListNewsAdapter(baseContext, response!!.body())
                adapter.notifyDataSetChanged()
                recycler_view_news.adapter = adapter
            }
        })

    }
}

