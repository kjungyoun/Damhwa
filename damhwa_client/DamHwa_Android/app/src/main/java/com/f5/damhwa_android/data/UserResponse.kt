package com.f5.damhwa_android.data

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("message")
    val message: String
)