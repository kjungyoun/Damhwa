package com.example.damhwa_android.ui.feeling

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.damhwa_android.R
import com.example.damhwa_android.base.BaseFragment
import com.example.damhwa_android.databinding.FragmentFeelingFlowerDetailBinding

class FeelingFlowerDetailFragment : BaseFragment<FragmentFeelingFlowerDetailBinding>(
    R.layout.fragment_feeling_flower_detail
) {
    private var feeling: String = "조금 우울한"
    private var flower: String = "매발톱"

    override fun init() {
        super.init()
        makeFeelingFlowerGuideText()
        binding.backButton.setOnClickListener {
            routeToFeelingPage()
        }
    }

    private fun routeToFeelingPage() =
        findNavController().navigate(R.id.action_feelingFlowerDetailFragment_to_feelingFragment)

    private fun makeFeelingFlowerGuideText() {
        val feelingText = getString(R.string.feeling_guide, feeling)
        val flowerText = getString(R.string.flower_guide, flower)

        val feelingTextStartIndex = feelingText.indexOf(feeling)
        val feelingTextEndIndex = feelingTextStartIndex + feeling.length

        val flowerTextStartIndex = flowerText.indexOf(flower)
        val flowerTextEndIndex = flowerTextStartIndex + flower.length

        val feelingSpannableText = SpannableStringBuilder(feelingText).apply {
            setSpan(
                ForegroundColorSpan(requireContext().resources.getColor(R.color.dark_green, null)),
                feelingTextStartIndex,
                feelingTextEndIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                StyleSpan(Typeface.BOLD),
                feelingTextStartIndex,
                feelingTextEndIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        val flowerSpannableText = SpannableStringBuilder(flowerText).apply {
            setSpan(
                ForegroundColorSpan(requireContext().resources.getColor(R.color.dark_green, null)),
                flowerTextStartIndex,
                flowerTextEndIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                StyleSpan(Typeface.BOLD),
                flowerTextStartIndex,
                flowerTextEndIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        binding.feelingFlowerFeelingText.setText(
            feelingSpannableText,
            TextView.BufferType.SPANNABLE
        )
        binding.feelingFlowerFlowerText.setText(
            flowerSpannableText,
            TextView.BufferType.SPANNABLE
        )
    }
}