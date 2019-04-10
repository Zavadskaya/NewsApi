package com.example.user.news.ViewHolder.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.news.ArticleActivity
import com.example.user.news.Interface.ItemClickListener
import com.example.user.news.Model.Headlines
import com.example.user.news.R
import com.example.user.news.ViewHolder.Holder.ListNewsViewHolder
import com.example.user.news.net.GlobalUrl
import com.squareup.picasso.Picasso


class ListNewsAdapter(private val context: Context, private val articles: Headlines?): RecyclerView.Adapter<ListNewsViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): ListNewsViewHolder {
        val inflater = LayoutInflater.from(p0!!.context)
        val itemView = inflater.inflate(R.layout.article_news_layout, p0, false)
        return ListNewsViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {
        holder.article_title.text = articles!!.articles[position].title.toString()
        Picasso.get()
                .load(articles.articles[position].urlToImage)
                .into(holder.imageView)
        holder.article_data.text = GlobalUrl.DateToTimeFormat(articles!!.articles[position].publishedAt!!.toString())


        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int) {

                val intent = Intent(context, ArticleActivity::class.java)
                intent.putExtra("description", this@ListNewsAdapter.articles.articles!![position].description)
                intent.putExtra("image",this@ListNewsAdapter.articles.articles!![position].urlToImage)
                intent.putExtra("title",this@ListNewsAdapter.articles.articles!![position].title)
                intent.putExtra("date",this@ListNewsAdapter.articles.articles!![position].publishedAt)
                intent.putExtra("author",this@ListNewsAdapter.articles.articles!![position].author)
                context.startActivity(intent)
            }
        })

    }
    override fun getItemCount(): Int = articles!!.articles!!.size
}

