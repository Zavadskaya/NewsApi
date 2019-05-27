package com.example.user.news.view.fragments

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager


abstract class EndlessRecyclerOnScrollListener(internal var layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {



    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        val isLoading = false
        if (!isLoading) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
}