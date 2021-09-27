package com.example.damhwa_android.network.service

import com.example.damhwa_android.data.StoryFlowers
import com.example.damhwa_android.ui.story.StoryFragmentViewModel.Letter
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface StoryService {
    @POST
    fun changeLetterToFlowers(@Body letterText: Letter): Single<StoryFlowers>
}