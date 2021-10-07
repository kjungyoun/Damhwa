package com.f5.damhwa_android.ui.calendar

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import com.f5.damhwa_android.R
import com.f5.damhwa_android.base.BaseFragment
import com.f5.damhwa_android.data.sharedpreferences.DamhwaSharedPreferencesImpl
import com.f5.damhwa_android.databinding.FragmentCalendarBinding


@SuppressLint("SetJavaScriptEnabled")
class CalendarFragment : BaseFragment<FragmentCalendarBinding>(
    R.layout.fragment_calendar
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        Handler().postDelayed({
            sendUserNo()
        }, 1000)
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        super.init()
        binding.calendar.settings.javaScriptEnabled = true
        binding.calendar.settings.domStorageEnabled = true
        binding.calendar.loadUrl("http://j5a503.p.ssafy.io:5000")
    }

    private fun sendUserNo() {
        val userno = DamhwaSharedPreferencesImpl.getUserNo()
        binding.calendar.evaluateJavascript(
            "sendUserNo($userno)",
            null
        )
    }
}