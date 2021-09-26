package com.example.damhwa_android.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.damhwa_android.RecommendedFlower
import com.example.damhwa_android.base.BaseViewModel

class StoryFragmentViewModel: BaseViewModel() {

    val letterText = MutableLiveData<String>()

    private var _recommendedFlowerList = MutableLiveData<ArrayList<RecommendedFlower>>()
    val recommendedFlowerList: MutableLiveData<ArrayList<RecommendedFlower>>
        get() = _recommendedFlowerList



}