package com.example.damhwa_android.ui.feeling

import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.damhwa_android.R
import com.example.damhwa_android.base.BaseFragment
import com.example.damhwa_android.ui.feeling.FeelingFragmentViewModel.FlowerRecommendedByFeeling
import com.example.damhwa_android.databinding.FragmentFeelingFlowerDetailBinding
import com.example.damhwa_android.network.DamhwaInjection
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.link.WebSharerClient
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
        binding.shareKakao.setOnClickListener {
            shareKakaoTalk()
        }
        feelingViewModel.recommendedFlowerFromFeeling
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { flowerInformation ->
                feeling = flowerInformation.description!!
                flower = flowerInformation.name!!
                makeFeelingFlowerGuideText()
                setInformation(flowerInformation)
                feelingViewModel.clearData()
            }
            .addToDisposable()
    }

    private fun setInformation(flowerInformation: FlowerRecommendedByFeeling) {
        binding.flowerDescription.text = flowerInformation.description
        binding.flowerName.text = flowerInformation.name
        Glide.with(requireActivity())
            .load("https://cdn.pixabay.com/photo/2015/04/19/08/32/marguerite-729510__480.jpg")
            .into(binding.flowerPic)
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
        checkKakaoTalkExist(defaultFeed)

    }

    private fun checkKakaoTalkExist(defaultFeed: FeedTemplate) {
        if (LinkClient.instance.isKakaoLinkAvailable(requireContext())) {
            LinkClient.rx.defaultTemplate(requireContext(), defaultFeed)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ linkResult ->
                    Log.d(TAG, "카카오링크 보내기 성공 ${linkResult.intent}")
                    startActivity(linkResult.intent)

                    Log.w(TAG, "Warning Msg: ${linkResult.warningMsg}")
                    Log.w(TAG, "Argument Msg: ${linkResult.argumentMsg}")
                }, { error ->
                    Log.e(TAG, "카카오링크 보내기 실패 ", error)
                })
                .addToDisposable()
        } else {
            val sharerUrl = WebSharerClient.instance.defaultTemplateUri(defaultFeed)

            // 1. CustomTabs으로 Chrome 브라우저 열기
            try {
                KakaoCustomTabsClient.openWithDefault(requireContext(), sharerUrl)
            } catch(e: UnsupportedOperationException) {
                // Chrome 브라우저가 없을 때 예외처리
            }

            // 2. CustomTabs으로 디바이스 기본 브라우저 열기
            try {
                KakaoCustomTabsClient.open(requireContext(), sharerUrl)
            } catch (e: ActivityNotFoundException) {
                // 인터넷 브라우저가 없을 때 예외처리
            }
        }
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