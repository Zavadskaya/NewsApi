package com.example.user.news

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AnimationUtils
import android.widget.ImageView
import java.lang.Thread.sleep

class SplashScreen : AppCompatActivity() {
    lateinit var  image: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        image = findViewById(R.id.images)

        var intent = Intent(this,MainActivity::class.java)
        var myAnim = AnimationUtils.loadAnimation(this,R.anim.apierance)
        image.startAnimation(myAnim)
        var thread = Thread()
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
