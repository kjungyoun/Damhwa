package com.example.damhwa_android.ui.story

import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.damhwa_android.R
import com.example.damhwa_android.base.BaseFragment
import com.example.damhwa_android.data.StoryFlower
import com.example.damhwa_android.databinding.FragmentStoryFlowerDetailBinding
import com.example.damhwa_android.ui.flowerstore.FlowerStoreActivity
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

class StoryFlowerDetailFragment : BaseFragment<FragmentStoryFlowerDetailBinding>(
    R.layout.fragment_story_flower_detail
) {
    private val disposables by lazy { CompositeDisposable() }
    lateinit var flower: StoryFlower

    override fun init() {
        super.init()
        getFlowerData()
        binding.backButton.setOnClickListener {
            routeToRecFlowerList()
        }
        binding.shareKakao.setOnClickListener {
            shareKakaoTalk()
        }
        binding.findFlower.setOnClickListener {
            startFindFlowerWebView()
        }
    }

    private fun startFindFlowerWebView() {
        val intent = Intent(requireActivity(), FlowerStoreActivity::class.java)
        startActivity(intent)
    }

    private fun getFlowerData() {
        arguments?.let {
            flower  = it.getSerializable("flower") as StoryFlower
            setFlowerData()
        }
    }

    private fun setFlowerData() {
        binding.flowerNameText.text = flower.name
        binding.flowerDescription.text = flower.description
        Glide.with(requireActivity())
            .load(flower.imageUrl)
            .centerCrop()
            .into(binding.flowerPic)
    }

    private fun shareKakaoTalk() {
        val defaultFeed = FeedTemplate(
            content = Content(
                title = flower.name,
                description = flower.description,
                imageUrl = flower.imageUrl,
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
                    Log.d(ContentValues.TAG, "카카오링크 보내기 성공 ${linkResult.intent}")
                    startActivity(linkResult.intent)

                    Log.w(ContentValues.TAG, "Warning Msg: ${linkResult.warningMsg}")
                    Log.w(ContentValues.TAG, "Argument Msg: ${linkResult.argumentMsg}")
                }, { error ->
                    Log.e(ContentValues.TAG, "카카오링크 보내기 실패 ", error)
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

    private fun routeToRecFlowerList() =
        findNavController().navigate(R.id.action_storyFlowerDetailFragment_to_storyRecFlowerFragment)

    private fun Disposable.addToDisposable(): Disposable = addTo(disposables)

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}