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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.damhwa_android.R
import com.example.damhwa_android.base.BaseFragment
import com.example.damhwa_android.data.FeelingFlower
import com.example.damhwa_android.databinding.FragmentFeelingFlowerDetailBinding
import com.example.damhwa_android.network.DamhwaInjection
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.link.WebSharerClient
import com.kakao.sdk.link.rx
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
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
    private lateinit var recommFlower: FeelingFlower

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
            .subscribe({ result ->
                recommFlower = result
                makeFeelingFlowerGuideText()
                setInformation(result)
                feelingViewModel.clearData()
            }, {
                Log.e("ErrorLogger - FeelingDetail - recommended", it.message.toString())
            })
            .addToDisposable()
    }

    private fun setInformation(feelingFlower: FeelingFlower) {
        binding.flowerDescription.text = feelingFlower.fContents
        Glide.with(requireActivity())
            .load(feelingFlower.watercolor_img)
            .into(binding.flowerPic)
    }

    private fun shareKakaoTalk() {
        val defaultFeed = FeedTemplate(
            content = Content(
                title = recommFlower.fNameKR,
                description = feelingViewModel.feelingHistory,
                imageUrl = recommFlower.watercolor_img,
                link = Link(
                    mobileWebUrl = "https://remarkable-nemophila-1b1.notion.site/3b83351d65d840158de5a5e9001d4a6c",
                    webUrl = "https://remarkable-nemophila-1b1.notion.site/3b83351d65d840158de5a5e9001d4a6c"
                )
            ),
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
            try {
                KakaoCustomTabsClient.openWithDefault(requireContext(), sharerUrl)
            } catch (e: UnsupportedOperationException) {
            }

            try {
                KakaoCustomTabsClient.open(requireContext(), sharerUrl)
            } catch (e: ActivityNotFoundException) {
            }
        }
    }

    private fun Disposable.addToDisposable(): Disposable = addTo(disposables)

    private fun routeToFeelingPage() =
        findNavController().navigate(R.id.action_feelingFlowerDetailFragment_to_feelingFragment)

    private fun makeFeelingFlowerGuideText() {
        val feelingText = getString(R.string.feeling_guide, recommFlower.emotionResult)
        val flowerText = getString(R.string.flower_guide, recommFlower.fNameKR)

        val feelingTextStartIndex = feelingText.indexOf(recommFlower.emotionResult)
        val feelingTextEndIndex = feelingTextStartIndex + recommFlower.emotionResult.length

        val flowerTextStartIndex = flowerText.indexOf(recommFlower.fNameKR)
        val flowerTextEndIndex = flowerTextStartIndex + recommFlower.fNameKR.length

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