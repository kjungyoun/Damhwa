package com.example.damhwa_android.ui.feeling

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.damhwa_android.base.BaseViewModel
import com.example.damhwa_android.repository.FeelingRepository

class FeelingFragmentViewModel(
    private val repository: FeelingRepository
) : BaseViewModel() {
    val feelingText = MutableLiveData<String>()

    fun changeTextToFlower() {
        Log.d("로그", feelingText.value!!)
        repository.changeFeelingToFlower(Feeling(feelingText = feelingText.value!!))
            .subscribe({ data ->
                Log.d("로그", data.toString())
            }, {
                Log.d("로그", it.message!!)
            })
    }

    data class Feeling(
        val feelingText: String
    )
}