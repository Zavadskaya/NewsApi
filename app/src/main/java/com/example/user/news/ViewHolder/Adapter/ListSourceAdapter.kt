package com.example.user.news.viewHolder.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import com.example.user.news.`interface`.ItemClickListener
import com.example.user.news.model.Sources
import com.example.user.news.view.EverythingActivity
import com.example.user.news.viewHolder.holder.ListSourceViewHolder


class ListSourceAdapter(private val context: Context, private val sour: Sources):RecyclerView.Adapter<ListSourceViewHolder>(),Filterable
{
    private var sourceSearchList: Sources

    init {
        this.sourceSearchList = sour
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSourceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(com.example.user.news.R.layout.source_news_layout,parent,false)
        return ListSourceViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ListSourceViewHolder, position: Int) {
        val source = sour.sources!![position]
       holder.source_title.text = source.name.toString()


        holder.setItemClickListener(object : ItemClickListener
        {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context, EverythingActivity::class.java)
                intent.putExtra("sources", sour.sources!![position].id)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)

            }
        })


    }
    override fun getItemCount(): Int =  sour.sources!!.size
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                Toast.makeText(context,"Filter",Toast.LENGTH_LONG).show()
            }

            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    sourceSearchList = sour
                } else {
                    for (row in sour.sources!!) {
                        if (row.name!!.toLowerCase().contains(charString.toLowerCase()) || row.description!!.contains(charSequence)) {
                        }
                    }
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = sourceSearchList
                return filterResults
            }

        }
    }



}


