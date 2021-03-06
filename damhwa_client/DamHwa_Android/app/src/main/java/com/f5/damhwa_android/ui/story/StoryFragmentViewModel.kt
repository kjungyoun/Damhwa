package com.f5.damhwa_android.ui.story

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.f5.damhwa_android.R
import com.f5.damhwa_android.base.BaseViewModel
import com.f5.damhwa_android.data.Flower
import com.f5.damhwa_android.data.History
import com.f5.damhwa_android.repository.StoryRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class StoryFragmentViewModel(
    private val storyRepository: StoryRepository
) : BaseViewModel() {

    var letterText = MutableLiveData<String>()
    var receiverText = MutableLiveData<String>()
    var letterHistory = ""
    var receiverHistory = ""

    private val _letterInputSubject: BehaviorSubject<Letter> =
        BehaviorSubject.createDefault(Letter())

    private val _isCompletedChangedToFlowerSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)
    val completeTrigger: Observable<Boolean> = _isCompletedChangedToFlowerSubject

    private val _changeFeelingToFlowerErrorIdSubject: PublishSubject<Int> = PublishSubject.create()

    private val _isChangingToFlowerSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)
    val isChanging: Observable<Boolean> = _isChangingToFlowerSubject

    private val _errorLogger: PublishSubject<String> = PublishSubject.create()
    val error: Observable<String> = _errorLogger

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
        letterText = MutableLiveData("")
        receiverText = MutableLiveData("")
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
                    saveHistoryInfo()
                    _recommendedFlowerFromStorySubject.onNext(
                        response
                    )
                }
                navigateToFlowerDetail()
            }, {
                _errorLogger.onNext("???????????? ???????????? ????????? ????????? ???????????????. \n ?????? ?????? ?????? ????????? ?????????!")
                Log.e("ErrorLogger - StoryFragmentViewModel - changeFlower", it.message.toString())
            })
            .addToDisposable()
    }

    private fun saveHistoryInfo() {
        receiverHistory = receiverText.value ?: ""
        letterHistory = letterText.value ?: ""
    }

    fun saveHistory(history: History) {
        storyRepository.saveHistory(history)
            .subscribeOn(Schedulers.io())
            .subscribe({ respone ->
                if (respone.statusCode != 200) {
                    _errorLogger.onNext("?????? ????????? ???????????? ???????????? ?????? ????????? ???????????????. \n ?????? ??????????????????!")
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