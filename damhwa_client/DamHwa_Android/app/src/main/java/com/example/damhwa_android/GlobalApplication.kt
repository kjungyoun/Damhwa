package com.example.damhwa_android

import android.app.Application
import com.example.damhwa_android.constants.Constants.KAKAO_KEY
import com.example.damhwa_android.data.sharedpreferences.DamhwaSharedPreferencesImpl
import com.kakao.sdk.common.KakaoSdk


class GlobalApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKakaoSDK()
        initializeSharedPreferences()
    }

    private fun initializeKakaoSDK() {
        KakaoSdk.init(this, KAKAO_KEY)
    }

    private fun initializeSharedPreferences() {
        DamhwaSharedPreferencesImpl.init(this)
    }
}
