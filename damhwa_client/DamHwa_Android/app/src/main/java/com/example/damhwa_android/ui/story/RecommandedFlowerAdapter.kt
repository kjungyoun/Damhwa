package com.example.damhwa_android.ui.story

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.damhwa_android.RecommendedFlower
import com.example.damhwa_android.databinding.ItemRecommendedFlowerBinding

class RecommendedFlowerAdapter():
    RecyclerView.Adapter<RecommendedFlowerAdapter.RecommendedFlowerViewHolder>() {
    var flowers = mutableListOf<RecommendedFlower>()

    class RecommendedFlowerViewHolder(val binding: ItemRecommendedFlowerBinding): RecyclerView.ViewHolder(binding.root) {
//        private val binding: ItemRecommendedFlowerBinding? = DataBindingUtil.bind(itemView)

        fun bind(flower: RecommendedFlower) {
            binding.flower = flower
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedFlowerViewHolder {
        val binding = ItemRecommendedFlowerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendedFlowerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendedFlowerViewHolder, position: Int) {
        holder.bind(flowers[position])
    }

    override fun getItemCount() = flowers.size

}