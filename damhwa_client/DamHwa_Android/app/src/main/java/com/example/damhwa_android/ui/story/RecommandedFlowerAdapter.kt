package com.example.damhwa_android.ui.story

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.damhwa_android.data.Flower
import com.example.damhwa_android.databinding.ItemRecommendedFlowerBinding

class RecommendedFlowerAdapter(
    private val navigate: (Flower) -> Unit
):
    RecyclerView.Adapter<RecommendedFlowerAdapter.RecommendedFlowerViewHolder>() {
    var flowers = emptyList<Flower>()

    inner class RecommendedFlowerViewHolder(val binding: ItemRecommendedFlowerBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position < flowers.size) {
                     val flower = flowers[position]
                    navigate(flower)
                }
            }
        }
        fun bind(flower: Flower) {
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