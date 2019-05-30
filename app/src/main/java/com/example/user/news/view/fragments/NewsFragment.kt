package com.example.user.news.view.fragments


import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.example.user.news.commons.SharedPrefer
import com.example.user.news.model.Article
import com.example.user.news.model.Headlines
import com.example.user.news.net.NewsService
import com.example.user.news.viewHolder.adapter.ListNewsAdapter
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_news.*
import retrofit2.Call
import retrofit2.Response
import kotlin.random.Random


open class NewsFragment : Fragment() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mAdapter: ListNewsAdapter
    private lateinit var mHandler: Handler
    var mRunnable: Runnable? = null

    var data = ArrayList<Article>()
    private var category: String = ""
    private var country: String = ""
    var n: Int = 0
    var isLoading = false
    var pagesize = 10
    var page = 1


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
        // Initialize the handler instance
        mHandler = Handler()

        arguments?.let {
            category = it.getString(ARG_TEXT, category)
            n = it.getInt("pos")
        }

        country = SharedPrefer.loadData(context!!)

        progress = view!!.findViewById(com.example.user.news.R.id.progressBar)
        recycler_view_news.setHasFixedSize(true)
        mAdapter = ListNewsAdapter(articles = data)
        recycler_view_news.adapter = mAdapter
        layoutManager = LinearLayoutManager(context)
        recycler_view_news.layoutManager = layoutManager


        loadWebSiteSource(category, page, pagesize, " ")

        recycler_view_news.addOnScrollListener(object : EndlessRecyclerOnScrollListener(layoutManager) {
            override fun loadMoreItems() {
                if (!isLoading) {
                    loadWebSiteSource(category, ++page, pagesize, " ")
                }
            }
        })


        refresh.setOnRefreshListener {
            mRunnable = Runnable {
                loadWebSiteSource(category, page, pagesize, " ")
                refresh.isRefreshing = false
            }
        }

        // Execute the task after specified time
        mHandler.postDelayed(
            mRunnable,
            (randomInRange(1, 5) * 1000).toLong()
        )
    }


    fun randomInRange(min: Int, max: Int): Int {
        val r = Random
        return r.nextInt((max - min) + 1) + min
    }


    fun loadWebSiteSource(category: String, page: Int, pagesize: Int, keyword: String) {
        progressBar.visibility = View.VISIBLE
        NewsService.instance.articles(
            country = country,
            pageSize = pagesize,
            page = page,
            category = category,
            keyword = keyword
        )
            .enqueue(object : retrofit2.Callback<Headlines> {
                override fun onFailure(call: Call<Headlines>?, t: Throwable?) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, "No wi-fi", Toast.LENGTH_SHORT).show()
                    loadFromRealm()
                }

                override fun onResponse(call: Call<Headlines>?, response: Response<Headlines>?) {
                    progressBar.visibility = View.GONE
                    if (response!!.isSuccessful) {
                        val list = response.body()!!.let { it }

                        if (page == 1)
                            data.clear()

                        data.addAll(list.articles)
                        mAdapter.notifyDataSetChanged()

                        saveToRealm(data)
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

    private fun loadFromRealm() {
        val realm = Realm.getDefaultInstance()

        val articles = realm
            .where(Article::class.java)
            .equalTo("category", category)
            .equalTo("country", country)
            .findAll()

        Log.d("REALM", "Articles size = ${articles.size}")

        data.clear()


        if (articles.size == 0) {
            Toast.makeText(context, "Realm is empty", Toast.LENGTH_LONG).show()
        } else {
            data.addAll(articles)
        }

        mAdapter.notifyDataSetChanged()
    }

    private fun saveToRealm(data: ArrayList<Article>) {
        val realm = Realm.getDefaultInstance()
        realm.use { realm ->
            realm.beginTransaction()
            for (article in data) {
                article.id = article.url.hashCode().toString()
                article.category = category
                article.country = country
            }
            realm.insertOrUpdate(data)
            realm.commitTransaction()

            Log.d("REALM", "Saved to realm")
            Toast.makeText(context, "Saved to realm", Toast.LENGTH_LONG).show()
        }
    }
}

