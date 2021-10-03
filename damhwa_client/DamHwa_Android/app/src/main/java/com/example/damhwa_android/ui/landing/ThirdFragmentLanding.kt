package com.example.damhwa_android.ui.landing

import android.content.ContentValues.TAG
import android.content.Intent
import com.example.damhwa_android.R
import android.util.Log
import android.widget.Toast
import com.example.damhwa_android.base.BaseFragment
import com.example.damhwa_android.databinding.FragmentThirdLandingBinding
import com.example.damhwa_android.ui.MainActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.AuthErrorCause.*
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.rx
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class ThirdFragmentLanding : BaseFragment<FragmentThirdLandingBinding>(R.layout.fragment_third_landing) {
    // BaseFragment를 상속했기 때문에, 굳이 return view가 없어도 됨.
    private val disposables by lazy { CompositeDisposable() }
    override fun init() {
        super.init()
        checkKakaoToken()
        binding.kakaoLoginButton.setOnClickListener {
            Single.just(UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext()))
                .flatMap { available ->
                    if (available) UserApiClient.rx.loginWithKakaoTalk(requireContext())
                    else UserApiClient.rx.loginWithKakaoAccount(requireContext())
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ token ->
                    UserApiClient.rx.me()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ user ->
                            Log.i(TAG, "사용자 정보 요청 성공" +
                                    "\n회원번호: ${user.id}" +
                                    "\n이메일: ${user.kakaoAccount?.email}" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                    "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                            Log.i(TAG, "로그인 성공 ${token.accessToken}")
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            startActivity(intent)
                        }, { error ->
                            Log.e(TAG, "사용자 정보 요청 실패", error)
                        })
                        .addTo(disposables)

                }, { error ->
                    Log.e(TAG, "로그인 실패", error)
                })
                .addToDisposable()
        }
    }

    private fun checkKakaoToken() {
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.d("ERROR:", error.toString())
                Toast.makeText(requireContext(), "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
            } else if (tokenInfo != null) {
                Toast.makeText(requireContext(), "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
        }
    }
    private fun Disposable.addToDisposable(): Disposable = addTo(disposables)

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

}