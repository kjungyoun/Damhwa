package com.example.damhwa_android.ui.story

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.damhwa_android.RecommendedFlower

object CustomBindingAdapter {
    @JvmStatic
    @BindingAdapter("flowers")
    fun setBindFlowers(view: RecyclerView, flowers: LiveData<ArrayList<RecommendedFlower>>) {
        view.adapter?.run {
            if (this is RecommendedFlowerAdapter) {
                flowers.value?.let { this.flowers = it } ?: { this.flowers = arrayListOf() }()
                this.notifyDataSetChanged()
            }
        }

    }

}