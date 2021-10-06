package com.example.damhwa_android.ui.story


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
import com.example.damhwa_android.databinding.FragmentStoryBinding
import com.example.damhwa_android.network.DamhwaInjection
import com.example.damhwa_android.ui.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo

class StoryFragment : BaseFragment<FragmentStoryBinding>(
    R.layout.fragment_story
) {
    private val disposables by lazy { CompositeDisposable() }
    private val storyViewModel by activityViewModels<StoryFragmentViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return StoryFragmentViewModel(DamhwaInjection.providerStoryRepository()) as T
            }
        }
    }

    override fun init() {
        super.init()
        binding.constraintStory.setOnClickListener() {
            hideKeyboard()
        }
        binding.changeFlower.setOnClickListener {
            storyViewModel.changeTextToFlower()
        }
        binding.storyViewModel = storyViewModel

        storyViewModel.completeTrigger
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ complete ->
                if (complete) {
                    routeToStoryRecFragment()
                }
            }, {
                Log.e("ErrorLogger - StoryFragment - storyViewModel.completeTrigger", it.toString())
            })
            .addToDisposable()

        storyViewModel.error
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ errorMsg ->
                DamwhaToast.createToast(requireContext(), errorMsg)?.show()
            }, {
                Log.e("ErrorLogger - StoryFragment - storyViewModel.error", it.toString())
            })
            .addToDisposable()

        storyViewModel.letterText.observe(this, Observer {
            storyViewModel.setLetterText()
            binding.changeFlower.isEnabled = it.length >= 1
        })

        storyViewModel.isChanging
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ status ->
                checkLoading(status)
            }, {
                Log.e("ErrorLogger - StoryFragment - storyViewModel.isChanging", it.toString())
            })
            .addToDisposable()
    }

    fun checkLoading(loading: Boolean) {
        when (loading) {
            true -> (activity as MainActivity).loadingDialog.show()
            false -> (activity as MainActivity).loadingDialog.dismiss()
        }
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.editText.windowToken, 0)
    }

    private fun routeToStoryRecFragment() =
        findNavController().navigate(R.id.action_storyFragment_to_storyRecFlowerFragment)

    private fun Disposable.addToDisposable(): Disposable = addTo(disposables)

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}