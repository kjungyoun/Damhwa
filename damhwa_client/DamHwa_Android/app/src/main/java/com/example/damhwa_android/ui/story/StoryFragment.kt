package com.example.damhwa_android.ui.story


import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.damhwa_android.R
import com.example.damhwa_android.base.BaseFragment
import com.example.damhwa_android.databinding.FragmentStoryBinding
import com.example.damhwa_android.network.DamhwaInjection
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

        binding.changeFlower.setOnClickListener {
            storyViewModel.changeTextToFlower()
        }
        binding.storyViewModel = storyViewModel

        storyViewModel.completeTrigger
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { complete ->
                if (complete) {
                    routeToStoryRecFragment()
                }
            }
            .addToDisposable()
        storyViewModel.letterText.observe(this, Observer {
            storyViewModel.setLetterText()
        })
    }

    private fun routeToStoryRecFragment() =
        findNavController().navigate(R.id.action_storyFragment_to_storyRecFlowerFragment)

    private fun Disposable.addToDisposable(): Disposable = addTo(disposables)

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}