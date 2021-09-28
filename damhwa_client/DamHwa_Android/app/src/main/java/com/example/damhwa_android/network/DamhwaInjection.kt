package com.example.damhwa_android.network

import com.example.damhwa_android.network.service.FeelingService
import com.example.damhwa_android.network.service.StoryService
import com.example.damhwa_android.repository.FeelingRepository
import com.example.damhwa_android.repository.StoryRepository

object DamhwaInjection {
    fun providerFeelingRepository(): FeelingRepository =
        FeelingRepository(DamhwaRetrofit.create(FeelingService::class.java))

    fun providerStoryRepository(): StoryRepository =
        StoryRepository(DamhwaRetrofit.create(StoryService::class.java))
}