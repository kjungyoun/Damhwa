package com.example.damhwa_android.repository

import com.example.damhwa_android.data.StoryFlowers
import com.example.damhwa_android.network.service.StoryService
import com.example.damhwa_android.ui.story.StoryFragmentViewModel.Letter
import io.reactivex.Single

class StoryRepository(
    private val storyService: StoryService
) {
    fun changeLetterToFlowers(letterText: Letter): Single<StoryFlowers> =
        storyService.changeLetterToFlowers(letterText)
}