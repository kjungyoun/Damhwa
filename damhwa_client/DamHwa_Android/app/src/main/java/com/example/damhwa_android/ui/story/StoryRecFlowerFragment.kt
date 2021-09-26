package com.example.damhwa_android.ui.story

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.damhwa_android.R
import com.example.damhwa_android.RecommendedFlower
import com.example.damhwa_android.base.BaseFragment
import com.example.damhwa_android.databinding.FragmentStoryRecFlowerBinding

class StoryRecFlowerFragment : BaseFragment<FragmentStoryRecFlowerBinding>(
    R.layout.fragment_story_rec_flower
) {
    override fun init() {
        super.init()
        binding.backButton.setOnClickListener {
            routeToBack()
        }

        val storyViewModel = ViewModelProvider(this).get(StoryFragmentViewModel::class.java)
        val adapter = RecommendedFlowerAdapter()
        binding.carouselRecycler.adapter = adapter

        adapter.flowers.addAll(loadFlowerData())
    }

    init {
        loadFlowerData()
    }

    private fun loadFlowerData(): ArrayList<RecommendedFlower> {
        val flower1: RecommendedFlower = RecommendedFlower(
            "https://young.hyundai.com/upload/CMS_NEWS_IMAGE/2018/06/15/CMS_NEWS_IMAGE_NFbumZE4KE5KNz6hhQOo.jpg",
            "데이지",
            "데이지 어쩌구저쩌구데이지 어쩌구저쩌구데이지 어쩌구저쩌구데이지 어쩌구저쩌구데이지 어쩌구저쩌구"
        )
        val flower2 = RecommendedFlower(
            "https://young.hyundai.com/upload/CMS_NEWS_IMAGE/2018/06/15/CMS_NEWS_IMAGE_Hf0nPugRC6NJn4ASyxuC.jpg",
            "매발톱",
            "독립의 의미를 가지고 있습니다."
        )
        val flower3 = RecommendedFlower(
            "https://young.hyundai.com/upload/CMS_NEWS_IMAGE/2018/06/15/CMS_NEWS_IMAGE_hQT1zQCOzIJFb29dKR1L.jpg",
            "핑크색꽃",
            "핑크 꽃은 내가 제일 좋아하는 꽃 ~~핑크 꽃은 내가 제일 좋아하는 꽃 ~~핑크 꽃은 내가 제일 좋아하는 꽃 ~~"
        )
        var tmpList = arrayListOf(
            flower1, flower2, flower3
        )
        return tmpList
    }

    private fun routeToBack() =
        findNavController().navigate(R.id.action_storyRecFlowerFragment_to_storyFragment)

    private fun routeToFlowerDetail() =
        findNavController().navigate(R.id.action_storyRecFlowerFragment_to_storyFlowerDetailFragment)

}