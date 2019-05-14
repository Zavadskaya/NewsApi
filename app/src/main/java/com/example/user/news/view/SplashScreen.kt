package com.example.user.news.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.user.news.R
import java.lang.Thread.sleep

class SplashScreen : AppCompatActivity() {
    lateinit var  image: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        image = findViewById(R.id.images)

        val intent = Intent(this, MainActivity::class.java)
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.apierance)
        image.startAnimation(myAnim)
        val thread = Thread()
        {
            run()
            {
                try {
                    sleep(2000)
                }
                finally {
                    startActivity(intent)
                    finish()
                }
            }
        }
        thread.start()
    }

}
