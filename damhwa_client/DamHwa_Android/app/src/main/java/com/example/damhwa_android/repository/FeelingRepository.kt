package com.example.damhwa_android.repository

import com.example.damhwa_android.data.FeelingFlower
import com.example.damhwa_android.network.service.FeelingService
import com.example.damhwa_android.ui.feeling.FeelingFragmentViewModel.Feeling
import com.google.gson.JsonObject
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class FeelingRepository(
    private val feelingService: FeelingService
) {
    fun changeFeelingToFlower(feeling: Feeling): Single<FeelingFlower> =
        feelingService.changeFeelingToFlower(feeling.toRequestBody())

    fun Feeling.toRequestBody(): RequestBody =
        JsonObject().apply {
            addProperty("state", feelingText)
        }
            .toString()
            .toRequestBody("application/json".toMediaTypeOrNull())
}
