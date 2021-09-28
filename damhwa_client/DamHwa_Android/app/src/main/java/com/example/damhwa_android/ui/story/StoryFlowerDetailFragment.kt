package com.example.damhwa_android.ui.story

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.damhwa_android.R
import com.example.damhwa_android.base.BaseFragment
import com.example.damhwa_android.databinding.FragmentStoryFlowerDetailBinding

class StoryFlowerDetailFragment : BaseFragment<FragmentStoryFlowerDetailBinding>(
    R.layout.fragment_story_flower_detail
) {
    override fun init() {
        super.init()
        binding.backButton.setOnClickListener {
            routeToRecFlowerList()
        }
        arguments?.let {
            val flower = it.getSerializable("flower")
            Log.d("꽃", flower.toString())
        }
    }

    private fun routeToRecFlowerList() =
        findNavController().navigate(R.id.action_storyFlowerDetailFragment_to_storyRecFlowerFragment)
}