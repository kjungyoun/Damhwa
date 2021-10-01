package com.example.damhwa_android.ui.feeling

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.damhwa_android.R
import com.example.damhwa_android.base.BaseFragment
import com.example.damhwa_android.custom.LoadingDialogFragment
import com.example.damhwa_android.databinding.FragmentFeelingBinding
import com.example.damhwa_android.network.DamhwaInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo

class FeelingFragment : BaseFragment<FragmentFeelingBinding>(
    R.layout.fragment_feeling
) {
    private val loadingDialogFragment by lazy { LoadingDialogFragment() }
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
            .subscribe({ complete ->
                if (complete) {
                    routeToFlowerFeelingDetail()
                }
            }, {
                Log.e("ErrorLogger - FeelingFragment - feelingViewModel.completeTrigger", it.toString())
            })
            .addToDisposable()

        feelingViewModel.isChanging
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ loading ->
                checkLoading(loading)
            }, {
                Log.e("ErrorLogger - StoryFragment - feelingViewModel.isChanging", it.toString())
            })
            .addToDisposable()
    }

    fun checkLoading(loading: Boolean) {
        when (loading) {
            true -> startLoadingSpinner()
            false -> hideLoadingSpinner()
        }
    }

    private fun startLoadingSpinner() {
        if (!loadingDialogFragment.isAdded){
            loadingDialogFragment.show(requireActivity().supportFragmentManager, "loader")
        }
    }

    private fun hideLoadingSpinner() {
        if (loadingDialogFragment.isAdded) {
            loadingDialogFragment.dismissAllowingStateLoss()
        }
    }

    private fun routeToFlowerFeelingDetail() =
        findNavController().navigate(R.id.action_feelingFragment_to_feelingFlowerDetailFragment)

    private fun Disposable.addToDisposable(): Disposable = addTo(disposables)

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}