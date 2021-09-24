package com.example.damhwa_android.ui.story


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
        val storyViewModel = ViewModelProvider(this).get(StoryFragmentViewModel::class.java)

        binding.changeFlower.setOnClickListener {
            routeToStoryRecFragment()
        }
        binding.storyViewModel = storyViewModel
    }

    private fun routeToStoryRecFragment() =
        findNavController().navigate(R.id.action_storyFragment_to_storyRecFlowerFragment)
}