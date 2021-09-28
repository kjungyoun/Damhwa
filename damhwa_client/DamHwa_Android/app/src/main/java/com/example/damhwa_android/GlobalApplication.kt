package com.example.damhwa_android

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
class GlobalApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "5d4ceabed4218c89d458e28bfdd4ed60")
    }
}
