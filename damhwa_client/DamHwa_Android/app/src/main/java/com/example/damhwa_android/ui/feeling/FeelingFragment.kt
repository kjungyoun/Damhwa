package com.example.damhwa_android.ui.feeling

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.damhwa_android.R
import com.example.damhwa_android.base.BaseFragment
import com.example.damhwa_android.databinding.FragmentFeelingBinding
import com.example.damhwa_android.network.DamhwaInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo

class FeelingFragment : BaseFragment<FragmentFeelingBinding>(
    R.layout.fragment_feeling
) {
    private val disposables by lazy { CompositeDisposable() }
    private val feelingViewModel by activityViewModels<FeelingFragmentViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return FeelingFragmentViewModel(DamhwaInjection.providerFeelingRepository()) as T
            }
        }
    }

    override fun init() {
        super.init()
        binding.changeFlower.setOnClickListener {
            feelingViewModel.changeTextToFlower()
        }
        binding.feelingViewModel = feelingViewModel
        feelingViewModel.feelingText.observe(this, Observer {
            feelingViewModel.setFeelingText()
        })

        feelingViewModel.completeTrigger
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { complete ->
                if (complete) {
                    routeToFlowerFeelingDetail()
                }
            }
            .addToDisposable()
    }

    private fun routeToFlowerFeelingDetail() =
        findNavController().navigate(R.id.action_feelingFragment_to_feelingFlowerDetailFragment)

    private fun Disposable.addToDisposable(): Disposable = addTo(disposables)

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}