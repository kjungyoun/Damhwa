package com.example.damhwa_android.ui.story

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.damhwa_android.R
import com.example.damhwa_android.base.BaseViewModel
import com.example.damhwa_android.data.Flower
import com.example.damhwa_android.data.History
import com.example.damhwa_android.repository.StoryRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class StoryFragmentViewModel(
    private val storyRepository: StoryRepository
) : BaseViewModel() {

    var letterText = MutableLiveData<String>()
    var letterHistory = ""

    private val _letterInputSubject: BehaviorSubject<Letter> =
        BehaviorSubject.createDefault(Letter())

    private val _isCompletedChangedToFlowerSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)
    val completeTrigger: Observable<Boolean> = _isCompletedChangedToFlowerSubject

    private val _changeFeelingToFlowerErrorIdSubject: PublishSubject<Int> = PublishSubject.create()

    private val _isChangingToFlowerSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)
    val isChanging: Observable<Boolean> = _isChangingToFlowerSubject

    private val _recommendedFlowerFromStorySubject: BehaviorSubject<List<Flower>> =
        BehaviorSubject.createDefault(emptyList())
    val recommendedFlowerListFromStory: Observable<List<Flower>> =
        _recommendedFlowerFromStorySubject

    fun setLetterText() {
        _letterInputSubject.onNext(
            _letterInputSubject.value?.copy(msg = letterText.value!!)
                ?: Letter(msg = letterText.value!!)
        )
    }

    fun clearData() {
        _letterInputSubject.onNext(Letter())
        _isCompletedChangedToFlowerSubject.onNext(false)
        letterHistory = letterText.value ?: ""
        letterText = MutableLiveData("")
    }

    fun changeTextToFlower() {
        _letterInputSubject.firstOrError()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _isChangingToFlowerSubject.onNext(true) }
            .flatMap { letterInput ->
                if (!letterInput.msg.isNullOrBlank()) {
                    storyRepository.changeLetterToFlowers(letterInput)
                } else {
                    _changeFeelingToFlowerErrorIdSubject.onNext(R.string.void_letter_text)
                    Single.error(
                        IllegalArgumentException(
                            "Failed to change letter to flower: StoryFragmentViewModel: $letterInput"
                        )
                    )
                }
            }
            .doOnSuccess { _isChangingToFlowerSubject.onNext(false) }
            .doOnError { _isChangingToFlowerSubject.onNext(false) }
            .subscribe({ response ->
                if (response != null) {
                    _recommendedFlowerFromStorySubject.onNext(
                        response
                    )
                }
                Log.d("로그", response.toString())
                navigateToFlowerDetail()
            }, {
                Log.e("ErrorLogger - StoryFragmentViewModel - changeFlower", it.message.toString())
            })
            .addToDisposable()
    }

    fun saveHistory(history: History) {
        storyRepository.saveHistory(history)
            .subscribeOn(Schedulers.io())
            .subscribe({ respone ->
                if (respone.statusCode != 200) {
                    Log.e("ErrorLogger - StoryFragmentViewModel - saveHistory", "Error in saving")
                }
            }, {
                Log.e("ErrorLogger - StoryFragmentViewModel - saveHistory", it.message.toString())
            })
            .addToDisposable()
    }

    fun navigateToFlowerDetail() {
        _isCompletedChangedToFlowerSubject.onNext(true)
    }

    data class Letter(
        val msg: String? = null,
    )
}