package com.example.user.news

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.example.user.news.net.GlobalUrl
import com.squareup.picasso.Picasso


class ArticleActivity : AppCompatActivity() {

    internal lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val description: TextView = findViewById(R.id.desc)
        val author: TextView = findViewById(R.id.author)
        val date: TextView = findViewById(R.id.publishedAt)
        val title:TextView = findViewById(R.id.title)
        val image: ImageView = findViewById(R.id.img)

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
    }
}




