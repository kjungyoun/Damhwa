package com.example.damhwa_android.ui.story

import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.damhwa_android.R
import com.example.damhwa_android.base.BaseFragment
import com.example.damhwa_android.custom.DamwhaToast
import com.example.damhwa_android.data.Flower
import com.example.damhwa_android.data.History
import com.example.damhwa_android.data.sharedpreferences.DamhwaSharedPreferencesImpl
import com.example.damhwa_android.databinding.FragmentStoryFlowerDetailBinding
import com.example.damhwa_android.network.DamhwaInjection
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.link.WebSharerClient
import com.kakao.sdk.link.rx
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
    private val storyViewModel by activityViewModels<StoryFragmentViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return StoryFragmentViewModel(DamhwaInjection.providerStoryRepository()) as T
            }
        }
    }
    lateinit var flower: Flower

    override fun init() {
        super.init()
        getFlowerData()
        binding.backButton.setOnClickListener {
            routeToRecFlowerList()
        }
        binding.shareKakao.setOnClickListener {
            shareKakaoTalk()
        }
    }

    private fun getFlowerData() {
        arguments?.let {
            flower = it.getSerializable("flower") as Flower
            setFlowerData()
        }
    }

    private fun setFlowerData() {
        binding.flowerNameText.text = flower.fNameKR
        binding.flowerDescription.text = flower.fContents
        Glide.with(requireActivity())
            .load(flower.watercolor_img)
            .centerCrop()
            .into(binding.flowerPic)
    }

    private fun shareKakaoTalk() {
        val defaultFeed = FeedTemplate(
            content = Content(
                title = flower.fNameKR,
                description = storyViewModel.letterHistory,
                imageUrl = flower.watercolor_img,
                link = Link(
                    mobileWebUrl = "https://developers.kakao.com"
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
                    Log.d(ContentValues.TAG, "카카오링크 보내기 성공 ${linkResult.intent}")
                    startActivity(linkResult.intent)
                    saveHistory()
                    Log.w(ContentValues.TAG, "Warning Msg: ${linkResult.warningMsg}")
                    Log.w(ContentValues.TAG, "Argument Msg: ${linkResult.argumentMsg}")
                }, { error ->
                    Log.e(ContentValues.TAG, "카카오링크 보내기 실패 ", error)
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

    private fun saveHistory() {
        storyViewModel.saveHistory(
            History(
                userNo = DamhwaSharedPreferencesImpl.getUserNo(),
                fNo = flower.fno,
                receiver = storyViewModel.receiverHistory,
                msg = storyViewModel.letterHistory,
                htype = true
            )
        )
    }

    private fun routeToRecFlowerList() =
        findNavController().navigate(R.id.action_storyFlowerDetailFragment_to_storyRecFlowerFragment)

    private fun Disposable.addToDisposable(): Disposable = addTo(disposables)

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}