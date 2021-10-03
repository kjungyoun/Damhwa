package com.example.damhwa_android.repository

import com.example.damhwa_android.data.Flower
import com.example.damhwa_android.network.service.StoryService
import com.example.damhwa_android.ui.story.StoryFragmentViewModel.Letter
import com.google.gson.JsonObject
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class StoryRepository(
    private val storyService: StoryService
) {
    fun changeLetterToFlowers(letterText: Letter): Single<List<Flower>> =
        storyService.changeLetterToFlowers(letterText.toResponseBody())

    fun Letter.toResponseBody(): RequestBody =
        JsonObject().apply {
            addProperty("msg", msg)
        }
            .toString()
            .toRequestBody("application/json".toMediaTypeOrNull())
}