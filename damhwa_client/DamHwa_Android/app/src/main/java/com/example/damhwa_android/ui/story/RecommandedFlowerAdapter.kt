package com.example.damhwa_android.ui.story

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.damhwa_android.R
import com.example.damhwa_android.data.StoryFlower
import com.example.damhwa_android.data.StoryFlowers
import com.example.damhwa_android.databinding.ItemRecommendedFlowerBinding

class RecommendedFlowerAdapter(
    private val navigate: (StoryFlower) -> Unit
):
    RecyclerView.Adapter<RecommendedFlowerAdapter.RecommendedFlowerViewHolder>() {
    var flowers = StoryFlowers()

    inner class RecommendedFlowerViewHolder(val binding: ItemRecommendedFlowerBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position < flowers.storyFlowers.size) {
                     val flower = flowers.storyFlowers[position]
                    navigate(flower)
                }
            }
        }
        fun bind(flower: StoryFlower) {
            binding.flower = flower
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedFlowerViewHolder {
        val binding = ItemRecommendedFlowerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendedFlowerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendedFlowerViewHolder, position: Int) {
        holder.bind(flowers.storyFlowers[position])
    }

    override fun getItemCount() = flowers.storyFlowers.size
}