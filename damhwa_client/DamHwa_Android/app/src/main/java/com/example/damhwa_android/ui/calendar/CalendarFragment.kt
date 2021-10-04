package com.example.damhwa_android.ui.calendar

import android.annotation.SuppressLint
import com.example.damhwa_android.R
import com.example.damhwa_android.base.BaseFragment
import com.example.damhwa_android.data.sharedpreferences.DamhwaSharedPreferencesImpl
import com.example.damhwa_android.databinding.FragmentCalendarBinding


@SuppressLint("SetJavaScriptEnabled")
class CalendarFragment: BaseFragment<FragmentCalendarBinding>(
    R.layout.fragment_calendar
) {
    override fun onResume() {
        super.onResume()
        sendUserNo()
    }

    override fun init() {
        super.init()
        binding.calendar.settings.javaScriptEnabled = true
        binding.calendar.addJavascriptInterface(CalendarInterface(requireContext(), binding.calendar), "CalendarBridge")
        binding.calendar.loadUrl("http://192.168.0.7:8080/")
    }

    private fun sendUserNo() {
        binding.calendar.post (Runnable {
            val userno = DamhwaSharedPreferencesImpl.getUserNo()
            binding.calendar.evaluateJavascript(
                "sendUserNo($userno)",
                null
            )
        })
    }
}