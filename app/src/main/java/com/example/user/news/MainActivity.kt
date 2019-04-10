package com.example.user.news

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.user.news.Interface.NewsService
import com.example.user.news.Model.Sources
import com.example.user.news.ViewHolder.Adapter.ListSourceAdapter
import com.example.user.news.net.GlobalUrl
import com.example.user.news.net.RetrofitClient

import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var service: NewsService
    lateinit var adapter: ListSourceAdapter
    lateinit var dialog: AlertDialog

    var mItemSelected: TextView? = null
    var country:String? = ""
    var category:String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var bottomNavigationView: BottomNavigationView = findViewById(R.id.navigation)
        mItemSelected = findViewById(R.id.tvItemSelected)

        var toolbar = findViewById<Toolbar>(R.id.tool)
        setSupportActionBar(toolbar)
        service = RetrofitClient.newsService

        recycler_view_source_news.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recycler_view_source_news.layoutManager = layoutManager

        dialog = SpotsDialog(this)
        dialog.show()
        dialog.setTitle("Loading...")
        dialog.setCancelable(true)
        loadWebSiteSource()

        bottomNavigationView.selectedItemId = R.id.menu_item1
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            intent = Intent(this, NewsActivity::class.java)
            when (item.itemId) {
                R.id.menu_item2 -> startActivity(intent)
            }
            true
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.list_menu, menu)
        var menuItem = menu.findItem(R.id.searchMenu)
        //var searchView = menuItem.actionView as SearchView
        //searchView.setOnQueryTextListener(this)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.countryFilter -> {
                var defaultPosition = 0
                val lists = Locale.getISOCountries()

                val builderSingle = AlertDialog.Builder(this)
                builderSingle.setTitle("Select language")
                builderSingle.setPositiveButton(getString(android.R.string.ok)) { dialog, _ -> dialog.dismiss() }
                builderSingle.setSingleChoiceItems(lists, defaultPosition) { _, which ->
                    defaultPosition = which
                    country = lists[which]
                    loadWebSiteSource()

                }
                builderSingle.show()
                return true
            }
            R.id.categoryFilter->
            {
                var defaultPosition = 0
                val lists = resources.getStringArray(R.array.categories)

                val builderSingle = AlertDialog.Builder(this)
                builderSingle.setTitle("Select category")
                builderSingle.setPositiveButton(getString(android.R.string.ok)) { dialog, _ -> dialog.dismiss() }
                builderSingle.setSingleChoiceItems(lists, defaultPosition) { _, which ->
                    defaultPosition = which
                    category = lists[which]
                    loadWebSiteSource()

                }
                builderSingle.show()
                return true
            }

        }

        return super.onOptionsItemSelected(item)

    }

    /*override fun onQueryTextChange(newText: String?): Boolean {
        var sourceInput: String = newText!!.toLowerCase()

        for (name: Source in dataListCopy) {
            if (name.name!!.toLowerCase().contains(sourceInput)) {
                dataListCopy.add(name)
            }
            adapter = ListSourceAdapter(baseContext, dataListCopy as Sources)
        }
        adapter.notifyDataSetChanged()
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }*/



    private fun loadWebSiteSource() {
        service.sources(country!!,category!!, GlobalUrl.API_KEY).enqueue(object : retrofit2.Callback<Sources> {
            override fun onFailure(call: Call<Sources>?, t: Throwable?) {
                Toast.makeText(baseContext, "Error", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(call: Call<Sources>?, response: Response<Sources>?) {
                /*dataList = response!!.body() as ArrayList<Source>
                dataListCopy = dataList.clone() as ArrayList<Source>*/
                adapter = ListSourceAdapter(baseContext, response!!.body())
                recycler_view_source_news.adapter = adapter
                adapter.notifyDataSetChanged()
                dialog.dismiss()

            }
        })
    }
}
