package com.example.damhwa_android.ui.story


import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.damhwa_android.R
import com.example.damhwa_android.base.BaseFragment
import com.example.damhwa_android.databinding.FragmentStoryBinding
import com.example.damhwa_android.network.DamhwaInjection

class StoryFragment : BaseFragment<FragmentStoryBinding>(
    R.layout.fragment_story
) {
    private val storyViewModel by activityViewModels<StoryFragmentViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return StoryFragmentViewModel(DamhwaInjection.providerStoryRepository()) as T
            }
        }
    }
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