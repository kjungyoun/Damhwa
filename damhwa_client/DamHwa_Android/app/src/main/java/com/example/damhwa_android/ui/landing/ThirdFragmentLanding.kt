package com.example.damhwa_android.ui.landing

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.damhwa_android.R
import com.example.damhwa_android.base.BaseFragment
import com.example.damhwa_android.data.sharedpreferences.DamhwaSharedPreferences
import com.example.damhwa_android.data.sharedpreferences.DamhwaSharedPreferencesImpl
import com.example.damhwa_android.databinding.FragmentThirdLandingBinding
import com.example.damhwa_android.network.DamhwaInjection
import com.example.damhwa_android.ui.MainActivity
import com.example.damhwa_android.ui.feeling.FeelingFragmentViewModel
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.rx
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class ThirdFragmentLanding :
    BaseFragment<FragmentThirdLandingBinding>(R.layout.fragment_third_landing) {
    private val disposables by lazy { CompositeDisposable() }
    private val landingViewModel by activityViewModels<LandingViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return LandingViewModel(DamhwaInjection.providerLandingRepository()) as T
            }
        }
    }

    override fun init() {
        super.init()
        binding.kakaoLoginButton.setOnClickListener {
            kakaoLogin()
        }
        landingViewModel.successLogin
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({ success ->
                if (success) {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                }
            }, {
                Log.e(
                    "ErrorLogger - LandingFragment - LandingViewModel.successLogin",
                    it.toString()
                )
            })
            .addToDisposable()
    }

    private fun kakaoLogin() {
        Single.just(UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext()))
            .flatMap { available ->
                if (available) UserApiClient.rx.loginWithKakaoTalk(requireContext())
                else UserApiClient.rx.loginWithKakaoAccount(requireContext())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ token ->
                getUserInfo()
                Log.i(TAG, "로그인 성공 ${token.accessToken}")
            }, { error ->
                Log.e(TAG, "로그인 실패", error)
            })
            .addToDisposable()
    }

    private fun getUserInfo() {
        UserApiClient.rx.me()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ user ->
                landingViewModel.login(
                    userNo = user.id,
                    username = user.kakaoAccount?.profile?.nickname,
                    email = user.kakaoAccount?.email,
                    profile = user.kakaoAccount?.profile?.thumbnailImageUrl
                )
            }, { error ->
                Log.e(TAG, "사용자 정보 요청 실패", error)
            })
            .addToDisposable()
    }

    private fun Disposable.addToDisposable(): Disposable = addTo(disposables)

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}