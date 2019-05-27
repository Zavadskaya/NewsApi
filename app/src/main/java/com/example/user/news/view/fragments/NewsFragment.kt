package com.example.user.news.view.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.example.user.news.R
import com.example.user.news.commons.SharedPrefer
import com.example.user.news.model.Article
import com.example.user.news.model.Headlines
import com.example.user.news.net.NewsService
import com.example.user.news.viewHolder.adapter.ListNewsAdapter
import kotlinx.android.synthetic.main.activity_news.*
import retrofit2.Call
import retrofit2.Response

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import kotlin.random.Random


open class NewsFragment : Fragment() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mAdapter: ListNewsAdapter
    var data: Headlines = Headlines(articles = RealmList())
    var text: String = ""
    var n: Int = 0
    var isLoading = false
    var pagesize = 10
    var page = 1

    lateinit var realm: Realm
    lateinit var configuration: RealmConfiguration
    lateinit var lists:RealmList<Article>


    var progress: ProgressBar? = null

    companion object {
        const val ARG_TEXT = "agrText"

        fun newInstance(text: String, n: Int): NewsFragment {
            val fragment = NewsFragment()
            val args = Bundle()
            args.putString(ARG_TEXT, text)
            args.putInt("pos", n)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(com.example.user.news.R.layout.activity_news, container, false)
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        arguments?.let {
            text = it.getString(ARG_TEXT, text)
            n = it.getInt("pos")
        }

        val contents = text

        progress = view!!.findViewById(R.id.progressBar)
        recycler_view_news.setHasFixedSize(true)
        mAdapter = ListNewsAdapter(articles = data)
        recycler_view_news.adapter = mAdapter
        layoutManager = LinearLayoutManager(context)
        recycler_view_news.layoutManager = layoutManager

        loadWebSiteSource(contents, page, pagesize, " ")


        recycler_view_news.addOnScrollListener(object : EndlessRecyclerOnScrollListener(layoutManager) {
            override fun loadMoreItems() {
                if (!isLoading) {


                    loadWebSiteSource(contents, ++page, pagesize, " ")
                    mAdapter.notifyDataSetChanged()
                }
            }
        })

        //realm
        Realm.init(context!!)

        configuration = RealmConfiguration.Builder()
            .name("newest.realm")
            .deleteRealmIfMigrationNeeded()
            .build()
        realm = Realm.getInstance(configuration)

    }

    fun loadWebSiteSource(category: String, page: Int, pagesize: Int, keyword: String) {
        progressBar.visibility = View.VISIBLE
        NewsService.instance.articles(
            country = SharedPrefer.loadData(context!!),
            pageSize = pagesize,
            page = page,
            category = category,
            keyword = keyword
        )
            .enqueue(object : retrofit2.Callback<Headlines> {
                override fun onFailure(call: Call<Headlines>?, t: Throwable?) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT)
                        .show()

                    /*val news = realm.where(Headlines::class.java).findAll()
                    for (i in 0 until news.size) {
                        val list = news[i]!!.articles
                        data.articles.addAll(list)
                    }*/
                }

                override fun onResponse(call: Call<Headlines>?, response: Response<Headlines>?) {
                    //TODO check if response success - response.isSuccessful
                    if (response!!.isSuccessful) {

                        val list = response.body()!!.let { it }
                        data.articles.addAll(list.articles)

                        /*val news = realm.where(Article::class.java).findAll()*/
                         realm.run {
                                beginTransaction()
                                copyToRealm(data.articles)
                                commitTransaction()
                            }

                            mAdapter.notifyDataSetChanged()




                        progressBar.visibility = View.GONE

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

