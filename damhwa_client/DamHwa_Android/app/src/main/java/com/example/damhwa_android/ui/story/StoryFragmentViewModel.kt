package com.example.damhwa_android.ui.story

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.damhwa_android.R
import com.example.damhwa_android.base.BaseViewModel
import com.example.damhwa_android.data.StoryFlower
import com.example.damhwa_android.data.StoryFlowers
import com.example.damhwa_android.repository.StoryRepository
import com.example.damhwa_android.ui.feeling.FeelingFragmentViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class StoryFragmentViewModel(
    private val storyRepository: StoryRepository
) : BaseViewModel() {

    val letterText = MutableLiveData<String>()

    private val _letterInputSubject: BehaviorSubject<Letter> =
        BehaviorSubject.createDefault(Letter())

    private val _isCompletedChangedToFlowerSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)
    val completeTrigger: Observable<Boolean> = _isCompletedChangedToFlowerSubject

    private val _changeFeelingToFlowerErrorIdSubject: PublishSubject<Int> = PublishSubject.create()

    private val isEnableChangeToFlowerSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)
    val setEnableButtonTrigger: Observable<Boolean> = isEnableChangeToFlowerSubject

    private val _isChangingToFlowerSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)
    val isChanging: Observable<Boolean> = _isChangingToFlowerSubject

    private val _recommendedFlowerFromStorySubject: BehaviorSubject<StoryFlowers> =
        BehaviorSubject.createDefault(StoryFlowers())
    val recommendedFlowerListFromStory: Observable<StoryFlowers> =
        _recommendedFlowerFromStorySubject


    fun setLetterText() {
        _letterInputSubject.onNext(
            _letterInputSubject.value?.copy(letterText = letterText.value!!)
                ?: Letter(letterText = letterText.value!!)
        )
    }

    fun changeTextToFlower() {
        _letterInputSubject.firstOrError()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _isChangingToFlowerSubject.onNext(true) }
            .flatMap { letterInput ->
                if (!letterInput.letterText.isNullOrBlank()) {
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
            .subscribe { response ->
                if (response != null) {
                    _recommendedFlowerFromStorySubject.onNext(
                        StoryFlowers(
                            response.storyFlowers
                        )
                    )
                }
                Log.d("로그", response.storyFlowers.toString())
                navigateToFlowerDetail()
            }
            .addToDisposable()
    }

    fun navigateToFlowerDetail() {
        _isCompletedChangedToFlowerSubject.onNext(true)
    }

    data class Letter(
        val letterText: String? = null,
    )
}