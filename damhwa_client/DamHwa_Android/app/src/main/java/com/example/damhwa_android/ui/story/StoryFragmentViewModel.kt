package com.example.damhwa_android.ui.story

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.damhwa_android.RecommendedFlower
import com.example.damhwa_android.base.BaseViewModel
import com.example.damhwa_android.data.RecommendedFlowers
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class StoryFragmentViewModel : BaseViewModel() {

    val letterText = MutableLiveData<String>()

    private val _recommendedFlowerList: BehaviorSubject<RecommendedFlowers> =
        BehaviorSubject.create()

    val recommendedFlowerList: Observable<RecommendedFlowers>
        get() = _recommendedFlowerList


    fun fetchRecommendedFlowers() {
        _recommendedFlowerList
            .onNext(loadFlowerData())
    }

    private fun loadFlowerData(): RecommendedFlowers {
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
        val tmpArr = RecommendedFlowers(recommendedFlowers = arrayListOf(flower1, flower2, flower3))
        Log.d("로그", tmpArr.toString())
        return tmpArr
    }

}