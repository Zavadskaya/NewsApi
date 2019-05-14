package com.example.user.news.view.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import com.example.user.news.`interface`.NewsService
import com.example.user.news.model.Headlines
import com.example.user.news.viewHolder.adapter.ListNewsAdapter
import kotlinx.android.synthetic.main.activity_news.*
import retrofit2.Call
import retrofit2.Response


open class NewsFragment : Fragment() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mAdapter: ListNewsAdapter
    var text: String = ""
    var n: Int = 0
    var isScrolling: Boolean = false
    var currentItem: Int = 0
    var totalItem: Int = 0
    var scrollOutItem: Int = 0

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

        //Paper.init(context)

        arguments?.let {
            text = it.getString(ARG_TEXT, text)
            n = it.getInt("pos")
        }

        val contents = text

        recycler_view_news.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        recycler_view_news.layoutManager = layoutManager
        loadWebSiteSource(" ", contents, " ")

        recycler_view_news.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        isScrolling = true
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    currentItem = layoutManager.childCount
                    totalItem = layoutManager.itemCount
                    scrollOutItem = layoutManager.findFirstVisibleItemPosition()

                    if (isScrolling && (currentItem + scrollOutItem == totalItem)) {
                        isScrolling = false
                        loadWebSiteSource(" ", contents, " ")
                    }
                }
            })

    }

    fun loadWebSiteSource(country: String, category: String, keyword: String) {
        /* val cache: String = Paper.book().read("cache")
         if (!cache.isEmpty() && cache != " ") {
             val headlines: Headlines = Gson().fromJson(cache, Headlines::class.java)

             mAdapter = ListNewsAdapter(context!!, headlines)
             mAdapter.notifyDataSetChanged()
             recycler_view_news.adapter = mAdapter
         } else {*/

        NewsService.instance.articles(country = country, category = category, keyword = keyword)
            .enqueue(object : retrofit2.Callback<Headlines> {
                override fun onFailure(call: Call<Headlines>?, t: Throwable?) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(call: Call<Headlines>?, response: Response<Headlines>?) {
                    mAdapter = response!!.body()?.let {
                        ListNewsAdapter(context!!, it)
                    }!!
                    recycler_view_news.adapter = mAdapter
                    mAdapter.notifyDataSetChanged()
                    //Paper.book().write("cache", Gson().toJson(response.body()))

                }
            })
    }

    /* }
   }, 4000)
            }*/


}

