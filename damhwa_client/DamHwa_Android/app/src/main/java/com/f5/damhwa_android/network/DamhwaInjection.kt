package com.f5.damhwa_android.network

import com.f5.damhwa_android.network.service.FeelingService
import com.f5.damhwa_android.network.service.LandingService
import com.f5.damhwa_android.network.service.StoryService
import com.f5.damhwa_android.repository.FeelingRepository
import com.f5.damhwa_android.repository.LandingRepository
import com.f5.damhwa_android.repository.StoryRepository

object DamhwaInjection {
    fun providerFeelingRepository(): FeelingRepository =
        FeelingRepository(DamhwaRetrofit.create(FeelingService::class.java))

    fun providerStoryRepository(): StoryRepository =
        StoryRepository(DamhwaRetrofit.create(StoryService::class.java))

    fun providerLandingRepository(): LandingRepository =
        LandingRepository(DamhwaRetrofit.create(LandingService::class.java))
}