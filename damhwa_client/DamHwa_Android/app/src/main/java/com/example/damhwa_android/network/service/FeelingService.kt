package com.example.damhwa_android.network.service

import com.example.damhwa_android.data.FeelingFlower
import com.example.damhwa_android.ui.feeling.FeelingFragmentViewModel.Feeling
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface FeelingService {
    @POST("/test")
    fun changeFeelingToFlower(@Body feeling: Feeling): Single<FeelingFlower>
}