package com.example.damhwa_android.ui.landing

import com.example.damhwa_android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.damhwa_android.base.BaseFragment
import com.example.damhwa_android.databinding.FragmentFirstLandingBinding

class FirstFragmentLanding : BaseFragment<FragmentFirstLandingBinding>(R.layout.fragment_first_landing) {
    override fun init() {
        super.init()

        // 이미지 뷰에 바인드해주는 라이브러리
        Glide.with(requireContext())
            .load(R.drawable.black_line)
            .fitCenter()
            .into(binding.bookBlackLine)
    }
}