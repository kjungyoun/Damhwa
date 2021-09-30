package com.example.damhwa_android

import android.app.Application
import com.example.damhwa_android.constants.Constants.KAKAO_KEY
import com.kakao.sdk.common.KakaoSdk


class GlobalApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, KAKAO_KEY)
    }
}
