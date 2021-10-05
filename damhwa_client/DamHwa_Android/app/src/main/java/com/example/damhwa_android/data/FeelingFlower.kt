package com.example.damhwa_android.data

import com.google.gson.annotations.SerializedName

data class FeelingFlower(
    @SerializedName("flower")
    val flower: Flower,
    @SerializedName("emotionResult")
    val emotionResult: String,
)
