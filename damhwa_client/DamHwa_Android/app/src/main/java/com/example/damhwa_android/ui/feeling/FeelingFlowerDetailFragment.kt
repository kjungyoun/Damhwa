package com.example.damhwa_android.ui.feeling

import android.content.ContentValues.TAG
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.damhwa_android.R
import com.example.damhwa_android.base.BaseFragment
import com.example.damhwa_android.databinding.FragmentFeelingFlowerDetailBinding
import com.example.damhwa_android.network.DamhwaInjection
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.link.rx
import com.kakao.sdk.template.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class FeelingFlowerDetailFragment : BaseFragment<FragmentFeelingFlowerDetailBinding>(
    R.layout.fragment_feeling_flower_detail
) {
    private val disposables by lazy { CompositeDisposable() }
    private val feelingViewModel by activityViewModels<FeelingFragmentViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return FeelingFragmentViewModel(DamhwaInjection.providerFeelingRepository()) as T
            }
        }
    }
    private lateinit var feeling: String
    private lateinit var flower: String

    override fun init() {
        super.init()
        binding.backButton.setOnClickListener {
            routeToFeelingPage()
        }
        binding.kakaoShare.setOnClickListener {
            shareKakaoTalk()
        }
        feelingViewModel.recommendedFlowerFromFeeling
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { flowerInformation ->
                feeling = flowerInformation.description!!
                flower = flowerInformation.name!!
                makeFeelingFlowerGuideText()
            }
            .addToDisposable()

    }

    private fun shareKakaoTalk() {
        val defaultFeed = FeedTemplate(
            content = Content(
                title = flower,
                description = feeling,
                imageUrl = "http://mud-kage.kakao.co.kr/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
                link = Link(
                    mobileWebUrl = "https://developers.kakao.com"
                )
            ),
            buttons = listOf(
                Button(
                    "앱으로 보기",
                    Link(
                        androidExecutionParams = mapOf("key1" to "value1", "key2" to "value2"),
                        iosExecutionParams = mapOf("key1" to "value1", "key2" to "value2")
                    )
                )
            )
        )
        LinkClient.rx.defaultTemplate(requireContext(), defaultFeed)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ linkResult ->
                Log.d(TAG, "카카오링크 보내기 성공 ${linkResult.intent}")
                startActivity(linkResult.intent)

                // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                Log.w(TAG, "Warning Msg: ${linkResult.warningMsg}")
                Log.w(TAG, "Argument Msg: ${linkResult.argumentMsg}")
            }, { error ->
                Log.e(TAG, "카카오링크 보내기 실패 ", error)
            })
            .addToDisposable()

    }

    private fun Disposable.addToDisposable(): Disposable = addTo(disposables)

    private fun routeToFeelingPage() =
        findNavController().navigate(R.id.action_feelingFlowerDetailFragment_to_feelingFragment)

    private fun makeFeelingFlowerGuideText() {
        val feelingText = getString(R.string.feeling_guide, feeling)
        val flowerText = getString(R.string.flower_guide, flower)

        val feelingTextStartIndex = feelingText.indexOf(feeling)
        val feelingTextEndIndex = feelingTextStartIndex + feeling.length

        val flowerTextStartIndex = flowerText.indexOf(flower)
        val flowerTextEndIndex = flowerTextStartIndex + flower.length

        val feelingSpannableText = SpannableStringBuilder(feelingText).apply {
            setSpan(
                ForegroundColorSpan(requireContext().resources.getColor(R.color.dark_green, null)),
                feelingTextStartIndex,
                feelingTextEndIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                StyleSpan(Typeface.BOLD),
                feelingTextStartIndex,
                feelingTextEndIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        val flowerSpannableText = SpannableStringBuilder(flowerText).apply {
            setSpan(
                ForegroundColorSpan(requireContext().resources.getColor(R.color.dark_green, null)),
                flowerTextStartIndex,
                flowerTextEndIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                StyleSpan(Typeface.BOLD),
                flowerTextStartIndex,
                flowerTextEndIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        binding.feelingFlowerFeelingText.setText(
            feelingSpannableText,
            TextView.BufferType.SPANNABLE
        )
        binding.feelingFlowerFlowerText.setText(
            flowerSpannableText,
            TextView.BufferType.SPANNABLE
        )
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}