package com.example.damhwa_android

import android.app.Application
import com.example.damhwa_android.constants.kakaoKey
import com.kakao.sdk.common.KakaoSdk


class GlobalApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        var myKakaoKey = kakaoKey
        KakaoSdk.init(this, myKakaoKey)
    }
}
