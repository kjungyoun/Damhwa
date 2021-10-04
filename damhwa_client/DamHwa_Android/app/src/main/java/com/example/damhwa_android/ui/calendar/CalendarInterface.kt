package com.example.damhwa_android.ui.calendar

import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast


class CalendarInterface(private val mContext: Context, private val webView: WebView) {
    @JavascriptInterface
    fun showToast(message: String) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
    }

}