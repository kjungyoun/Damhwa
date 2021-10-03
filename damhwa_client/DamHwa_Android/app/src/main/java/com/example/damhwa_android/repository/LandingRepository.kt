package com.example.damhwa_android.repository

import com.example.damhwa_android.data.User
import com.example.damhwa_android.data.UserResponse
import com.example.damhwa_android.network.service.LandingService
import com.google.gson.JsonObject
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class LandingRepository(
    private val landingService: LandingService
) {
    fun login(user: User): Single<UserResponse> {
        return landingService.login(
            user.toRequestBody()
        )
    }

    fun User.toRequestBody(): RequestBody =
        JsonObject().apply {
            addProperty("userno", userNo)
            addProperty("email", email)
            addProperty("username", username)
            addProperty("profile", profile)
        }
            .toString()
            .toRequestBody("application/json".toMediaTypeOrNull())
}