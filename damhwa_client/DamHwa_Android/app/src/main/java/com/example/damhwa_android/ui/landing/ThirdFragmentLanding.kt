package com.example.damhwa_android.ui.landing

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

class ThirdFragmentLanding : BaseFragment<FragmentThirdLandingBinding>(R.layout.fragment_third_landing) {
    // BaseFragment를 상속했기 때문에, 굳이 return view가 없어도 됨.
    override fun init() {
        super.init()
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

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(requireContext(), "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(requireContext(), "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(requireContext(), "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(requireContext(), "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(requireContext(), "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(requireContext(), "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(requireContext(), "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(requireContext(), "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Log.d("로그:", error.toString())
                        Toast.makeText(requireContext(), "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else if (token != null) {
                Toast.makeText(requireContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        }
        binding.kakaoLoginButton.setOnClickListener {
            if(UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())){
                UserApiClient.instance.loginWithKakaoTalk(requireContext(), callback = callback)
            }else{
                UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
            }
        }
    }
}