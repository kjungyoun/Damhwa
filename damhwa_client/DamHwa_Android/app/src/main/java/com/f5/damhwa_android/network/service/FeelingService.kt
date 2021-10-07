package com.f5.damhwa_android.network.service

import com.f5.damhwa_android.data.FeelingFlower
import com.f5.damhwa_android.data.HistoryResponse
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface FeelingService {
    @POST("api/recomm/state")
    fun changeFeelingToFlower(@Body body: RequestBody): Single<FeelingFlower>

    @POST("history/save")
    fun saveHistory(@Body body: RequestBody): Single<HistoryResponse>
}