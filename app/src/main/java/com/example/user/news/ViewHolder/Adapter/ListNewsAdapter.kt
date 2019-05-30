package com.example.user.news.viewHolder.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.news.`interface`.ItemClickListener
import com.example.user.news.model.Article
import com.example.user.news.net.GlobalUrl
import com.example.user.news.view.ArticleActivity
import com.example.user.news.viewHolder.holder.ListNewsViewHolder
import com.squareup.picasso.Picasso


class ListNewsAdapter(articles: ArrayList<Article>) :
    RecyclerView.Adapter<ListNewsViewHolder>() {

    var sourceSearchList: List<Article> = articles


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(com.example.user.news.R.layout.article_news_layout, parent, false)
        return ListNewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {
        holder.article_title.text = sourceSearchList[position].title.toString()
        val image = sourceSearchList[position].urlToImage.toString()
        if (image != null) {
            Picasso.get()
                .load(sourceSearchList[position].urlToImage)
                .into(holder.imageView)
        }
        else {
            holder.imageView.setImageResource(com.example.user.news.R.drawable.news)
        }

        holder.article_data.text = GlobalUrl.DateToTimeFormat(sourceSearchList[position].publishedAt!!.toString())


        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int) {
                view.context.let {
                    it.startActivity(Intent(it, ArticleActivity::class.java).apply {
                        putExtra("description", this@ListNewsAdapter.sourceSearchList[position].description)
                        putExtra("image", this@ListNewsAdapter.sourceSearchList[position].urlToImage)
                        putExtra("title", this@ListNewsAdapter.sourceSearchList[position].title)
                        putExtra("date", this@ListNewsAdapter.sourceSearchList[position].publishedAt)
                        putExtra("author", this@ListNewsAdapter.sourceSearchList[position].author)
                        putExtra("url", this@ListNewsAdapter.sourceSearchList[position].url)

                    })
                }
            }
        })
    }

    override fun getItemCount() = sourceSearchList.size

    override fun getItemId(position: Int): Long {
        return sourceSearchList[position].url?.let {
            it.hashCode().toLong()
        } ?: position.toLong()
    }


}

