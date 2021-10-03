package com.example.damhwa_android.network.service

import com.example.damhwa_android.data.User
import com.example.damhwa_android.data.UserResponse
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface LandingService {
    @POST("auth/kakao/login")
    fun login(@Body user: RequestBody): Single<UserResponse>
}