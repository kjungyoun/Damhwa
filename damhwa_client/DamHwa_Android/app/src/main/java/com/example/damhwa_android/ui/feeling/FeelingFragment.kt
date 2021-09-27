package com.example.damhwa_android.ui.feeling

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.damhwa_android.R
import com.example.damhwa_android.base.BaseFragment
import com.example.damhwa_android.databinding.FragmentFeelingBinding

class FeelingFragment : BaseFragment<FragmentFeelingBinding>(
    R.layout.fragment_feeling
) {
    override fun init() {
        super.init()
        binding.changeFlower.setOnClickListener {
            routeToFlowerFeelingDetail()
        }
        val feelingViewModel =
            ViewModelProvider(requireActivity()).get(FeelingFragmentViewModel::class.java)
        binding.feelingViewModel = feelingViewModel
    }

    private fun routeToFlowerFeelingDetail() =
        findNavController().navigate(R.id.action_feelingFragment_to_feelingFlowerDetailFragment)
}