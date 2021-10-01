package com.example.damhwa_android.ui.flowerstore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.example.damhwa_android.R

class FlowerStoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flower_store)

        val webView: WebView = findViewById(R.id.flowerStoreWebView)
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(WebAppInterface(this), "FlowerStore" )

        webView.loadUrl("file:///android_asset/sample.html")


    }

}