package com.example.damhwa_android.network

import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object DamhwaRetrofit {

    const val API_END_POINT = "http://j5a503.p.ssafy.io:8080/"

    fun <T> create(
        service: Class<T>,
    ): T = Retrofit.Builder()
        .baseUrl(API_END_POINT)
        .client(provideOkHTTPClient(provideLoggingIntercepter()))
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()
        .create(service)

    private fun provideOkHTTPClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    private fun provideLoggingIntercepter(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }
    }
}