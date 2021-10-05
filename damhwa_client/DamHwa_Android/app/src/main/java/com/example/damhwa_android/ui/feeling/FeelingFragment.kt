package com.example.damhwa_android.ui.feeling

import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.damhwa_android.R
import com.example.damhwa_android.base.BaseFragment
import com.example.damhwa_android.custom.DamwhaToast
import com.example.damhwa_android.databinding.FragmentFeelingBinding
import com.example.damhwa_android.network.DamhwaInjection
import com.example.damhwa_android.ui.MainActivity
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
    // editText 밖 터치시 키보드 내리기
    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.writeFeelingEditText.windowToken, 0)
    }

    override fun init() {
        super.init()

        binding.constraintFeeling.setOnClickListener() {
            hideKeyboard()
        }

        binding.changeFlower.setOnClickListener {
            feelingViewModel.changeTextToFlower()
        }
        binding.feelingViewModel = feelingViewModel
        feelingViewModel.feelingText.observe(this, Observer {
            feelingViewModel.setFeelingText()
            binding.changeFlower.isEnabled = it.length >= 1
        })

        feelingViewModel.error
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ errorMsg ->
                DamwhaToast.createToast(requireContext(), errorMsg)?.show()
            }, {
                Log.e("ErrorLogger - FeelingFragment - feelingViewModel.error", it.toString())
            })
            .addToDisposable()

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
            true -> (activity as MainActivity).loadingDialog.show()
            false -> (activity as MainActivity).loadingDialog.dismiss()
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