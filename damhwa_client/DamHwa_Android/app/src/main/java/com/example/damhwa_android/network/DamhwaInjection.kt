package com.example.damhwa_android.network

import com.example.damhwa_android.network.service.FeelingService
import com.example.damhwa_android.repository.FeelingRepository

object DamhwaInjection {
    fun providerFeelingRepository(): FeelingRepository = FeelingRepository(DamhwaRetrofit.create(FeelingService::class.java))
}