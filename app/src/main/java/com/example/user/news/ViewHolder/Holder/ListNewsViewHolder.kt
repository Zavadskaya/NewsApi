package com.example.user.news.viewHolder.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.user.news.`interface`.ItemClickListener
import kotlinx.android.synthetic.main.article_news_layout.view.*

class ListNewsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener {

    private lateinit var itemClickListener: ItemClickListener
    var article_title = itemView.article_news_name
    var imageView = itemView.news_image
    var article_data = itemView.article_date

    init {
        itemView.setOnClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View?) {
        itemClickListener.onClick(v!!, adapterPosition)
    }
}