package com.example.damhwa_android.network.service

import com.example.damhwa_android.data.Flower
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface StoryService {
    @POST("api/recomm/msg")
    fun changeLetterToFlowers(@Body letterText: RequestBody): Single<List<Flower>>
}