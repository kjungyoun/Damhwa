package com.example.damhwa_android.ui.feeling

import androidx.lifecycle.MutableLiveData
import com.example.damhwa_android.R
import com.example.damhwa_android.base.BaseViewModel
import com.example.damhwa_android.data.FeelingFlower
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

    private val _recommendedFlowerFromFeelingSubject: BehaviorSubject<FeelingFlower> =
        BehaviorSubject.create()
    val recommendedFlowerFromFeeling: Observable<FeelingFlower> =
        _recommendedFlowerFromFeelingSubject

    fun changeTextToFlower() {
        _feelingInputSubject.firstOrError()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _isChangingToFlowerSubject.onNext(true) }
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
            .doOnSuccess { _isChangingToFlowerSubject.onNext(false) }
            .doOnError { _isChangingToFlowerSubject.onNext(false) }
            .subscribe { response ->
                if (response != null) {
                    _recommendedFlowerFromFeelingSubject.onNext(
                        response
                    )
                }
                navigateToFlowerDetail()
            }
            .addToDisposable()
    }

    fun navigateToFlowerDetail() {
        _isCompletedChangedToFlowerSubject.onNext(true)
    }

    fun clearData() {
        _feelingInputSubject.onNext(Feeling())
        _isCompletedChangedToFlowerSubject.onNext(false)
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