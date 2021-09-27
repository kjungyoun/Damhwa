package com.example.damhwa_android.network.service

import com.example.damhwa_android.data.FeelingFlower
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface FeelingService {
    @POST("test")
    fun changeFeelingToFlower(@Body body: RequestBody): Single<FeelingFlower>
}