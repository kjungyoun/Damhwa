package com.example.damhwa_android.data

import com.google.gson.annotations.SerializedName

data class StoryFlower(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image_url")
    val imageUrl: String
)
