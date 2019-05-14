package com.example.user.news.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.user.news.R
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
    lateinit var alertDialog:SpotsDialog

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)


        alertDialog = SpotsDialog(this)
        alertDialog.show()


        val web: WebView = this.findViewById(R.id.web)
        web.settings.javaScriptEnabled = true
        web.webChromeClient = WebChromeClient()
        web.webViewClient = object: WebViewClient()
        {
            override fun onPageFinished(view: WebView?, url: String?) {
                   alertDialog.dismiss()
            }
        }
        if(intent!=null)
        {
                web.loadUrl(intent.getStringExtra("url"))

        }

    }
    override fun onBackPressed() {
        when {
            web!!.canGoBack() -> web.goBack()
            else -> super.onBackPressed()
        }
    }
}
