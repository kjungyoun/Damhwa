package com.f5.damhwa_android.ui.landing

import android.content.Intent
import android.util.Log
import com.bumptech.glide.Glide
import com.f5.damhwa_android.R
import com.f5.damhwa_android.base.BaseFragment
import com.f5.damhwa_android.databinding.FragmentFirstLandingBinding
import com.f5.damhwa_android.ui.MainActivity
import com.kakao.sdk.user.UserApiClient

class FirstFragmentLanding :
    BaseFragment<FragmentFirstLandingBinding>(R.layout.fragment_first_landing) {
    override fun init() {
        super.init()

        checkKakaoToken()
        setBookImage()
    }

    private fun checkKakaoToken() {
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.d("ERROR:", error.toString())
            } else if (tokenInfo != null) {
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