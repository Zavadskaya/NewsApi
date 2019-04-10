package com.example.user.news.ViewHolder.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.user.news.Interface.ItemClickListener
import com.example.user.news.Model.Sources
import com.example.user.news.R
import com.example.user.news.ViewHolder.Holder.ListSourceViewHolder


class ListSourceAdapter(private val context: Context, private val sources: Sources?):RecyclerView.Adapter<ListSourceViewHolder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): ListSourceViewHolder {
        val inflater = LayoutInflater.from(p0!!.context)
        val itemView = inflater.inflate(R.layout.source_news_layout,p0,false)
        return ListSourceViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ListSourceViewHolder, position: Int) {
        holder!!.source_title.text = sources!!.sources!![position].name.toString()

        holder.setItemClickListener(object : ItemClickListener
        {
            override fun onClick(view: View, position: Int) {
                Toast.makeText(context, "In process", Toast.LENGTH_SHORT).show()
            }
        })

    }
    override fun getItemCount(): Int = sources!!.sources!!.size

}


