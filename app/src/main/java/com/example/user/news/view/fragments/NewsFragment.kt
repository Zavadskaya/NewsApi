package com.example.user.news.view.fragments



import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.util.Range
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
import kotlinx.android.synthetic.main.activity_news.*
import retrofit2.Call
import retrofit2.Response

import io.realm.Realm
import io.realm.RealmList
import kotlin.random.Random



open class NewsFragment : Fragment() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mAdapter: ListNewsAdapter
    private lateinit var mHandler: Handler
    var mRunnable: Runnable? = null
    // var data: Headlines = Headlines(articles = RealmList())
    var data = RealmList<Article>()
    var text: String = ""
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


        val realmdb = Realm.getDefaultInstance()
        realmdb.beginTransaction()
        //realmdb.deleteAll()
        realmdb.commitTransaction()
        // Initialize the handler instance
        mHandler = Handler()

        arguments?.let {
            text = it.getString(ARG_TEXT, text)
            n = it.getInt("pos")
        }

        val contents = text

        progress = view!!.findViewById(com.example.user.news.R.id.progressBar)
        recycler_view_news.setHasFixedSize(true)
        mAdapter = ListNewsAdapter(articles = data)
        recycler_view_news.adapter = mAdapter
        layoutManager = LinearLayoutManager(context)
        recycler_view_news.layoutManager = layoutManager


        loadWebSiteSource(contents,page,pagesize," ")
        //loadWebSiteSource(contents, page, pagesize, " ")

        recycler_view_news.addOnScrollListener(object : EndlessRecyclerOnScrollListener(layoutManager) {
            override fun loadMoreItems() {
                if (!isLoading) {
                    loadWebSiteSource(contents, ++page, pagesize, " ")
                    mAdapter.notifyDataSetChanged()
                }
            }
        })


        refresh.setOnRefreshListener {
            mRunnable = Runnable {
                loadWebSiteSource(contents,page,pagesize," ")
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
            country = SharedPrefer.loadData(context!!),
            pageSize = pagesize,
            page = page,
            category = category,
            keyword = keyword
        )
            .enqueue(object : retrofit2.Callback<Headlines> {
                override fun onFailure(call: Call<Headlines>?, t: Throwable?) {
                    //Toast.makeText(context, "No wi-fi", Toast.LENGTH_SHORT)
                        //.show()
                    loadFromRealm()
                    progressBar.visibility = View.GONE
                }

                override fun onResponse(call: Call<Headlines>?, response: Response<Headlines>?) {
                    //TODO check if response success - response.isSuccessful
                    if (response!!.isSuccessful) {
                        data.clear()
                        val list = response.body()!!.let { it }
                        data.addAll(list.articles)

                        val realm = Realm.getDefaultInstance()

                        realm.beginTransaction()
                            for(article in data) {
                                article.id = article.url.hashCode().toString()
                                realm.copyToRealm(article.articles)
                                Toast.makeText(context,"Save to realm",Toast.LENGTH_LONG).show()
                            }
                        realm.commitTransaction()
                        realm.close()

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

    fun loadFromRealm() {
       val  realmd = Realm.getDefaultInstance()
        if(realmd.isEmpty) {
            Toast.makeText(context, "Realm is empty", Toast.LENGTH_LONG).show()
        }
        else {
            val values = realmd.where(Headlines::class.java).findAll()
            realmd.beginTransaction()
            for(i in 0 until values.size) {
                for (articleList in values) {
                    realmd.copyFromRealm(articleList.articles)
                    data.addAll(articleList.articles)
                    mAdapter.notifyDataSetChanged()
                }
            }
            realmd.commitTransaction()
            realmd.close()
        }
    }


}

