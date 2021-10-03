package com.example.damhwa_android.repository

import com.example.damhwa_android.data.User
import com.example.damhwa_android.data.UserResponse
import com.example.damhwa_android.network.service.LandingService
import com.google.gson.JsonObject
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class LandingRepository(
    private val landingService: LandingService
) {
    fun login(user: User): Single<UserResponse> {
        return landingService.login(JsonObject().apply {
            addProperty("userno", user.userNo)
            addProperty("email", user.email)
            addProperty("username", user.username)
            addProperty("profile", user.profile)
        }
            .toString()
            .toRequestBody("application/json".toMediaTypeOrNull())
        )
    }
}