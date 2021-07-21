package com.github.andresvasquez.kotlinevents.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import com.github.andresvasquez.kotlinevents.databinding.FragmentSplashBinding
import com.github.andresvasquez.kotlinevents.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment() {
    override val viewModel: SplashViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSplashBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setHasOptionsMenu(true)
        addSwipeListener(binding)
        return binding.root
    }

    private fun addSwipeListener(binding: FragmentSplashBinding) {
        val transitionListener = object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, startId: Int, endId: Int) {
            }

            override fun onTransitionChange(
                p0: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }

            override fun onTransitionCompleted(p0: MotionLayout?, currentId: Int) {
                viewModel.navigateToTheNextScreen()
            }

            override fun onTransitionTrigger(
                p0: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }
        }
        binding.motionLayout.setTransitionListener(transitionListener)
    }
}