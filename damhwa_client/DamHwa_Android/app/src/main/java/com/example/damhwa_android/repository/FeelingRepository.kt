package com.example.damhwa_android.repository

import android.util.Log
import com.example.damhwa_android.data.FeelingFlower
import com.example.damhwa_android.network.service.FeelingService
import com.example.damhwa_android.ui.feeling.FeelingFragmentViewModel.Feeling
import io.reactivex.Single

class FeelingRepository(
    private val feelingService: FeelingService
) {
    fun changeFeelingToFlower(feeling: Feeling): Single<FeelingFlower> =
        feelingService.changeFeelingToFlower(feeling)
}
