package com.example.user.news.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import com.example.user.news.R
import com.example.user.news.view.fragments.BottomSheet
import com.example.user.news.view.fragments.NewsFragment
import com.example.user.news.viewHolder.adapter.SearchFragmentAdapter


class SearchActivity : AppCompatActivity(), BottomSheet.OnInputListener{
    lateinit var bottomNavigationView: BottomNavigationView
    val newsFragment = NewsFragment.newInstance("general", 0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        val viewPager = findViewById<ViewPager>(R.id.viewpager)
        setupViewPager(viewPager)

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)

        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbars)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)



        bottomNavigationView = findViewById(R.id.navigation)
        bottomNavigationView.selectedItemId = R.id.menu_item2
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            intent = Intent(this, MainActivity::class.java)
            val intent2 =Intent(this,SourceActivity::class.java)
            when (item.itemId) {
                R.id.menu_item1 -> startActivity(intent)

                R.id.menu_item2 -> baseContext
                R.id.menu_item3 -> startActivity(intent2)
            }
            true
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.list_menu, menu)
        val menuItem = menu.findItem(R.id.searchMenu)
        val menuItm = menu.findItem(R.id.filter)
           menuItm.setOnMenuItemClickListener{
               showBottomSheetDialogFragment()
               true
           }
        val searchView = menuItem.actionView as SearchView
        val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.length > 2) {

                    newsFragment.loadWebSiteSource(" ", 1, 10, query)
                }
                return false
            }


            override fun onQueryTextChange(newText: String): Boolean {
                newsFragment.loadWebSiteSource(" ", 1,10," ")

                return false
            }
        })

        return true
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SearchFragmentAdapter(supportFragmentManager)
            adapter.addFragment(newsFragment,"Titles")

        viewPager.adapter = adapter
    }
    private fun showBottomSheetDialogFragment() {
        val bottomSheetFragment = BottomSheet()
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }
    override fun sendInput(input: String,output:String) {
        newsFragment.loadWebSiteSource(input, 1, 10," ")
    }

}







