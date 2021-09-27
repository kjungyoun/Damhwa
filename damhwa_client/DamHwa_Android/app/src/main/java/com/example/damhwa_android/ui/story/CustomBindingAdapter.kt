package com.example.damhwa_android.ui.story

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object CustomBindingAdapter {
    @JvmStatic
    @BindingAdapter("flowerUrl")
    fun setBindFlowerPictureUrl(view: ImageView, flowerPictureUrl: String) {
        Log.d("로그", flowerPictureUrl)
        Glide.with(view.context)
            .load(flowerPictureUrl)
            .centerCrop()
            .into(view)
    }
}