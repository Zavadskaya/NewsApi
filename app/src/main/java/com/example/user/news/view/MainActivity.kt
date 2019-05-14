package com.example.user.news.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.example.user.news.R.*
import com.example.user.news.view.fragments.FragmentDialog
import com.example.user.news.view.fragments.NewsFragment
import com.example.user.news.viewHolder.adapter.FragmentAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), FragmentDialog.OnInputListener {

    lateinit var bottomNavigationView: BottomNavigationView

    var newsTabs: ArrayList<NewsFragment> = ArrayList()
    val adapter = FragmentAdapter(supportFragmentManager)

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        val viewPager = findViewById<ViewPager>(id.viewpager_main)
        setupViewPager(viewPager)

        val tabLayout = findViewById<TabLayout>(id.tabs_main)
        tabLayout.setupWithViewPager(viewPager)

        val mFab = findViewById<FloatingActionButton>(id.fabButton)

        mFab.setOnClickListener {
            chamarMyDialog()
        }

        bottomNavigationView = findViewById(id.navigation)
        bottomNavigationView.selectedItemId = id.menu_item1
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            intent = Intent(this, SearchActivity::class.java)
            val intent2 = Intent(this, SourceActivity::class.java)

            when (item.itemId) {
                id.menu_item1 -> baseContext
                id.menu_item2 -> startActivity(intent)
                id.menu_item3 -> startActivity(intent2)
            }
            true
        }
    }


    private fun setupViewPager(viewPager: ViewPager) {
        val arrayCategory = resources.getStringArray(array.categories)

        for (i in 0 until arrayCategory.size) {
            val newsFragment = NewsFragment.newInstance(arrayCategory[i].toString(), i)
            adapter.addFragment(newsFragment, arrayCategory[i].toString())
            newsTabs.add(newsFragment)
        }
        viewPager.adapter = adapter
    }


    private fun chamarMyDialog() {
        FragmentDialog().show(supportFragmentManager, "my_fragment")
    }

    //FIXME Change update date to reload ViewPager and get category from Shared Prefs
    //https://stackoverflow.com/questions/18088076/update-fragment-from-viewpager
    override fun sendInput(input: String) {
        newsTabs[tabs_main.selectedTabPosition].loadWebSiteSource(input, "", "")
    }
}




