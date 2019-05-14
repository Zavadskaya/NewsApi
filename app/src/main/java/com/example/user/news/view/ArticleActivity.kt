package com.example.user.news.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.example.user.news.R
import com.example.user.news.net.GlobalUrl
import com.squareup.picasso.Picasso


class ArticleActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val description: TextView = findViewById(R.id.desc)
        val author: TextView = findViewById(R.id.author)
        val date: TextView = findViewById(R.id.publishedAt)
        val title:TextView = findViewById(R.id.title)
        val image: ImageView = findViewById(R.id.img)
        val webView:TextView = findViewById(R.id.webView)

        if (intent != null) {
            if (!intent.getStringExtra("description").isEmpty()
                    ||!intent.getStringExtra("title").isEmpty()
                    ||!intent.getStringExtra("date").isEmpty())
                description.text = intent.getStringExtra("description")
                author.text = intent.getStringExtra("author")
                date.text = GlobalUrl.DateToTimeFormat(intent.getStringExtra("date"))
                title.text = intent.getStringExtra("title")
                Picasso.get()
                    .load(intent.getStringExtra("image"))
                    .into(image)

        }
        webView.setOnClickListener {
            val intents = Intent(this, WebViewActivity::class.java)
            intents.putExtra("url", intent.getStringExtra("url"))
            startActivity(intents)
        }


    }
}



