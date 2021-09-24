package com.example.damhwa_android.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.damhwa_android.base.BaseViewModel

class StoryFragmentViewModel: BaseViewModel() {
    val _letterText = MutableLiveData<String>()

}