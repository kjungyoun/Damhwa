package com.example.damhwa_android.ui.feeling

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.damhwa_android.R
import com.example.damhwa_android.base.BaseViewModel
import com.example.damhwa_android.data.FeelingFlower
import com.example.damhwa_android.data.History
import com.example.damhwa_android.repository.FeelingRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class FeelingFragmentViewModel(
    private val repository: FeelingRepository
) : BaseViewModel() {
    var feelingText = MutableLiveData<String>()

    private val _feelingInputSubject: BehaviorSubject<Feeling> =
        BehaviorSubject.createDefault(Feeling())

    private val _isCompletedSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)
    val completeTrigger: Observable<Boolean> = _isCompletedSubject

    private val _changeFeelingToFlowerErrorIdSubject: PublishSubject<Int> = PublishSubject.create()

    private val isEnableChangeToFlowerSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)
    val setEnableButtonTrigger: Observable<Boolean> = isEnableChangeToFlowerSubject

    private val _isChangingSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)
    val isChanging: Observable<Boolean> = _isChangingSubject

    private val _recommFlowerSubject: BehaviorSubject<FeelingFlower> =
        BehaviorSubject.create()
    val recommendedFlowerFromFeeling: Observable<FeelingFlower> =
        _recommFlowerSubject

    fun changeTextToFlower() {
        _feelingInputSubject.firstOrError()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _isChangingSubject.onNext(true) }
            .flatMap { feelingInput ->
                if (!feelingInput.feelingText.isNullOrBlank()) {
                    repository.changeFeelingToFlower(feelingInput)
                } else {
                    _changeFeelingToFlowerErrorIdSubject.onNext(R.string.void_feeling_text)
                    Single.error(
                        IllegalArgumentException(
                            "Failed to change feeling to flower: FeelingFragmentViewModel: $feelingInput"
                        )
                    )
                }
            }
            .doOnSuccess { _isChangingSubject.onNext(false) }
            .doOnError { _isChangingSubject.onNext(false) }
            .subscribe { response ->
                if (response != null) {
                    _recommFlowerSubject.onNext(
                        response
                    )
                }
                navigateToFlowerDetail()
            }
            .addToDisposable()
    }

    fun saveHistory(history: History) {
        repository.saveHistory(history)
            .subscribeOn(Schedulers.io())
            .subscribe({ respone ->
                if (respone.statusCode != 200) {
                    Log.e("ErrorLogger - FeelingFragmentViewModel - saveHistory", "Error in saving")
                }
            }, {
                Log.e("ErrorLogger - FeelingFragmentViewModel - saveHistory", it.message.toString())
            })
            .addToDisposable()
    }

    fun navigateToFlowerDetail() {
        _isCompletedSubject.onNext(true)
    }

    fun clearData() {
        _feelingInputSubject.onNext(Feeling())
        _isCompletedSubject.onNext(false)
        feelingText = MutableLiveData("")
    }

    fun setFeelingText() {
        _feelingInputSubject.onNext(
            _feelingInputSubject.value?.copy(feelingText = feelingText.value)
                ?: Feeling(feelingText = feelingText.value)
        )
    }

    data class Feeling(
        val feelingText: String? = null
    )

}