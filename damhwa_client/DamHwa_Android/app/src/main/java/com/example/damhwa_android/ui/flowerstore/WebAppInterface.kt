package com.example.damhwa_android.ui.flowerstore

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast

class WebAppInterface(
    private val mContext: Context
) {
    @JavascriptInterface
    fun showToast(message: String) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
    }
}