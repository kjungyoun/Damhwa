package com.example.damhwa_android.ui.landing

import android.content.Intent
import com.example.damhwa_android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.damhwa_android.ui.MainActivity
import com.kakao.sdk.user.UserApiClient

class SecondFragmentLanding : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.fragment_second_landing, container, false)
        checkKakaoToken()
        return view
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
}