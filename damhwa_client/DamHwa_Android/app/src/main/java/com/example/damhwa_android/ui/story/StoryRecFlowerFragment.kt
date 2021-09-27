package com.example.damhwa_android.ui.story

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.damhwa_android.R
import com.example.damhwa_android.base.BaseFragment
import com.example.damhwa_android.databinding.FragmentStoryRecFlowerBinding

class StoryRecFlowerFragment : BaseFragment<FragmentStoryRecFlowerBinding>(
    R.layout.fragment_story_rec_flower
) {
    override fun init() {
        super.init()
        binding.backButton.setOnClickListener {
            routeToBack()
        }

        val storyViewModel = ViewModelProvider(requireActivity()).get(StoryFragmentViewModel::class.java)
        val adapter = RecommendedFlowerAdapter()
        binding.carouselRecycler.adapter = adapter

        storyViewModel.recommendedFlowerList
            .subscribe({
                adapter.flowers = it.recommendedFlowers
                adapter.notifyDataSetChanged()
            })
    }


    private fun routeToBack() =
        findNavController().navigate(R.id.action_storyRecFlowerFragment_to_storyFragment)

    private fun routeToFlowerDetail() =
        findNavController().navigate(R.id.action_storyRecFlowerFragment_to_storyFlowerDetailFragment)

}