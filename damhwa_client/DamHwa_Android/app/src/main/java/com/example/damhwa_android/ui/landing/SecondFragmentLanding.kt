package com.example.damhwa_android.ui.landing

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.damhwa_android.R
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
            } else if (tokenInfo != null) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
        }
    }
}