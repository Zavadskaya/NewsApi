package com.example.user.news.ViewHolder.Adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.news.ArticleActivity
import com.example.user.news.Interface.ItemClickListener
import com.example.user.news.Model.Article
import com.example.user.news.R
import com.example.user.news.ViewHolder.Holder.ListNewsViewHolder
import com.example.user.news.net.GlobalUrl
import com.squareup.picasso.Picasso


class ListNewsAdapter(private val data: List<Article>) : RecyclerView.Adapter<ListNewsViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListNewsViewHolder = ListNewsViewHolder(
        LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.article_news_layout, viewGroup, false)
    )

    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {
        val article: Article = data[position]

        Picasso.get().load(article.urlToImage).into(holder.imageView)
        holder.article_title.text = article.title.toString()
        holder.article_data.text = GlobalUrl.DateToTimeFormat(article.publishedAt!!.toString())
        //FIXME What is publishedAt will be null? ^^

        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int) {
                view.context.let {
                    it.startActivity(Intent(it, ArticleActivity::class.java).apply {
                        putExtra("description", article.description)
                        putExtra("image", article.urlToImage)
                        putExtra("title", article.title)
                        putExtra("date", article.publishedAt)
                        putExtra("author", article.author)
                    })
                }
            }
        })
    }

    override fun getItemCount(): Int = data.size
    override fun getItemId(position: Int): Long =
        data[position].url.hashCode().toLong() //FIXME need to change to data[position].id instead of 'url', if not NULL
}

