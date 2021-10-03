package com.example.damhwa_android.network

import android.content.SharedPreferences
import com.example.damhwa_android.data.sharedpreferences.DamhwaSharedPreferences
import com.example.damhwa_android.data.sharedpreferences.DamhwaSharedPreferencesImpl
import com.example.damhwa_android.network.service.FeelingService
import com.example.damhwa_android.network.service.LandingService
import com.example.damhwa_android.network.service.StoryService
import com.example.damhwa_android.repository.FeelingRepository
import com.example.damhwa_android.repository.LandingRepository
import com.example.damhwa_android.repository.StoryRepository

object DamhwaInjection {
    fun providerFeelingRepository(): FeelingRepository =
        FeelingRepository(DamhwaRetrofit.create(FeelingService::class.java))

    fun providerStoryRepository(): StoryRepository =
        StoryRepository(DamhwaRetrofit.create(StoryService::class.java))

    fun providerLandingRepository(): LandingRepository =
        LandingRepository(DamhwaRetrofit.create(LandingService::class.java))
}