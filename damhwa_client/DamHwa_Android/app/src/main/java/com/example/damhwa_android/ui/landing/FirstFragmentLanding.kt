package com.example.damhwa_android.ui.landing

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.damhwa_android.R
import com.bumptech.glide.Glide
import com.example.damhwa_android.base.BaseFragment
import com.example.damhwa_android.databinding.FragmentFirstLandingBinding
import com.example.damhwa_android.ui.MainActivity
import com.kakao.sdk.user.UserApiClient

class FirstFragmentLanding : BaseFragment<FragmentFirstLandingBinding>(R.layout.fragment_first_landing) {
    override fun init() {
        super.init()

        checkKakaoToken()
        setBookImage()
    }

    private fun checkKakaoToken() {
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.d("ERROR:", error.toString())
                Toast.makeText(requireContext(), "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
            } else if (tokenInfo != null) {
                Toast.makeText(requireContext(), "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
        }
    }

    private fun setBookImage() {
        Glide.with(requireContext())
            .load(R.drawable.black_line)
            .fitCenter()
            .into(binding.bookBlackLine)
    }

}