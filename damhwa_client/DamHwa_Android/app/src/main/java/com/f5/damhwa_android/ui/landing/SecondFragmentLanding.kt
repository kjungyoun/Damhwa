package com.f5.damhwa_android.ui.landing

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.f5.damhwa_android.R
import com.f5.damhwa_android.ui.MainActivity
import com.kakao.sdk.user.UserApiClient

class SecondFragmentLanding : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.fragment_second_landing, container, false)
        return view
    }
}