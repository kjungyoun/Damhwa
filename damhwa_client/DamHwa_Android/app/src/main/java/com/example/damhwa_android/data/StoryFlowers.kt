package com.example.damhwa_android.data

import com.google.gson.annotations.SerializedName

data class StoryFlowers(
    @SerializedName("flowers")
    val storyFlowers: List<StoryFlower> = emptyList()
)