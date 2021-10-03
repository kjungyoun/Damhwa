package com.example.damhwa_android.ui.landing

import android.util.Log
import com.example.damhwa_android.base.BaseViewModel
import com.example.damhwa_android.data.User
import com.example.damhwa_android.data.UserResponse
import com.example.damhwa_android.repository.LandingRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class LandingViewModel(
    private val repository: LandingRepository
) : BaseViewModel() {
    private var _successLogin: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)
    val successLogin get() = _successLogin
    fun login(
        userNo: Long,
        email: String?,
        username: String?,
        profile: String?
    ) {
        repository.login(
            User(
                username = username,
                userNo = userNo,
                profile = profile,
                email = email
            )
        )
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                Log.e("로그", response.toString())
                if (response.statusCode == 200) {
                    _successLogin.onNext(true)
                } else {
                    Log.e("ErrorLogger - LandingViewModel - login", response.message)
                }
            }, {
                Log.e("ErrorLogger - LandingViewModel - login", it.toString())
            })
            .addToDisposable()
    }
}