package com.example.user.news.viewHolder.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.news.R
import com.example.user.news.`interface`.ItemClickListener
import com.example.user.news.model.Headlines
import com.example.user.news.net.GlobalUrl
import com.example.user.news.view.ArticleActivity
import com.example.user.news.viewHolder.holder.ListNewsViewHolder
import com.squareup.picasso.Picasso

class ListNewsAdapter (private val context: Context, private val articles: Headlines): RecyclerView.Adapter<ListNewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(com.example.user.news.R.layout.article_news_layout, parent, false)
        return ListNewsViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {
        holder.article_title.text = articles.articles[position].title.toString()
    if(articles.articles[position].urlToImage!=null)
            Picasso.get()
                .load(articles.articles[position].urlToImage)
                .into(holder.imageView)
        else{
            holder.imageView.setImageResource(R.drawable.news)

        }

        holder.article_data.text = GlobalUrl.DateToTimeFormat(articles.articles[position].publishedAt!!.toString())


        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int) {
                view.context.let {
                    it.startActivity(Intent(it, ArticleActivity::class.java).apply {
                        putExtra("description", this@ListNewsAdapter.articles.articles[position].description)
                        putExtra("image", this@ListNewsAdapter.articles.articles[position].urlToImage)
                        putExtra("title", this@ListNewsAdapter.articles.articles[position].title)
                        putExtra("date", this@ListNewsAdapter.articles.articles[position].publishedAt)
                        putExtra("author", this@ListNewsAdapter.articles.articles[position].author)
                        putExtra("url", this@ListNewsAdapter.articles.articles[position].url)

                    })
                }
            }
        })

    }
    override fun getItemCount():Int
    {return articles.articles.size}
    override fun getItemId(position: Int): Long =
        articles!!.articles[position].url!!.hashCode().toLong()


}

