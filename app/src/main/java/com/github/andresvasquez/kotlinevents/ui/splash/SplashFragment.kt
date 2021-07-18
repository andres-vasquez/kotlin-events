package com.github.andresvasquez.kotlinevents.ui.splash

import com.udacity.nano.popularmovies.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment() {
    override val viewModel: SplashViewModel by viewModel()
}