package com.github.andresvasquez.kotlinevents.ui.detail

import com.udacity.nano.popularmovies.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventDetailFragment : BaseFragment() {
    override val viewModel: EventDetailViewModel by viewModel()
}