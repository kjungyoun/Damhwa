package com.f5.damhwa_android.network.service

import com.f5.damhwa_android.data.Flower
import com.f5.damhwa_android.data.HistoryResponse
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface StoryService {
    @POST("api/recomm/msg")
    fun changeLetterToFlowers(@Body body: RequestBody): Single<List<Flower>>

    @POST("history/save")
    fun saveHistory(@Body body: RequestBody): Single<HistoryResponse>
}