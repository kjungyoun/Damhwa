package com.example.damhwa_android.ui.story


import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.damhwa_android.R
import com.example.damhwa_android.base.BaseFragment
import com.example.damhwa_android.databinding.FragmentStoryBinding

class StoryFragment : BaseFragment<FragmentStoryBinding>(
    R.layout.fragment_story
) {
    override fun init() {
        super.init()
        val storyViewModel = ViewModelProvider(requireActivity()).get(StoryFragmentViewModel::class.java)

        binding.changeFlower.setOnClickListener {
            storyViewModel.fetchRecommendedFlowers()
            routeToStoryRecFragment()
        }
        binding.storyViewModel = storyViewModel
    }

    private fun routeToStoryRecFragment() =
        findNavController().navigate(R.id.action_storyFragment_to_storyRecFlowerFragment)
}