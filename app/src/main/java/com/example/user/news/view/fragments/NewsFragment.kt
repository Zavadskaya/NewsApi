package com.example.user.news.view.fragments


import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ProgressBar
import android.widget.Toast
import com.example.user.news.Common.SharedPrefer
import com.example.user.news.model.Headlines
import com.example.user.news.net.NewsService
import com.example.user.news.viewHolder.adapter.ListNewsAdapter
import retrofit2.Call
import retrofit2.Response
import java.util.*
import android.R.bool
import android.R
import retrofit2.Callback


open class NewsFragment : Fragment() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mAdapter: ListNewsAdapter
    var text: String = ""
    var n: Int = 0
    private var previousTotal = 0 // The total number of items in the dataset after the last load
    private var loading = true // True if we are still waiting for the last set of data to load.
    private val visibleThreshold = 5 // The minimum amount of items to have below your current scroll position before loading more.
    var firstVisibleItem: Int = 0
    var visibleItemCount:Int = 0
    var totalItemCount:Int = 0

    private var current_page = 1

    var progress:ProgressBar? = null




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

        progress = view!!.findViewById<ProgressBar>(com.example.user.news.R.id.progressBar)
        recycler_view_news.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        recycler_view_news.layoutManager = layoutManager
        loadWebSiteSource(contents, " ")

        recycler_view_news.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        loading = true
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                    if (loading)
                    {
                        if (totalItemCount > previousTotal)
                        {
                            loading = false
                            previousTotal = totalItemCount
                        }
                        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold))
                        {
                            current_page++
                            Log.e("Page",current_page++.toString())
                            loadWebSiteSource(contents," ")
                            loading = true
                        }

                        /*if ((visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            LoadMoreEvent(this, null);
                        }*/
                    }
                }
            })

    }

    fun loadWebSiteSource(category: String, keyword: String) {
        progressBar.visibility = View.VISIBLE
            NewsService.instance.articles(
                country = SharedPrefer.loadData(context!!),
                category = category,
                keyword = keyword
            )
                .enqueue(object : Callback<Headlines> {
                    override fun onFailure(call: Call<Headlines>?, t: Throwable?) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onResponse(call: Call<Headlines>?, response: Response<Headlines>?) {
                        //TODO check if response success - response.isSuccessful
                        if (response!!.isSuccessful) {
                            mAdapter = response.body()?.let {
                                ListNewsAdapter(it)
                            }!!
                            recycler_view_news.adapter = mAdapter
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

