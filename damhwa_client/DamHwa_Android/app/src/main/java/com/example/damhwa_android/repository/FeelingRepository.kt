package com.example.damhwa_android.repository

import com.example.damhwa_android.data.FeelingFlower
import com.example.damhwa_android.network.service.FeelingService
import io.reactivex.Single

class FeelingRepository(
    private val feelingService: FeelingService
) {
    fun changeFeelingToFlower(text: String): Single<FeelingFlower> =
        feelingService.changeFeelingToFlower(text)
}
