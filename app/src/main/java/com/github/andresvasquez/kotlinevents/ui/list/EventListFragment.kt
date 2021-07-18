package com.github.andresvasquez.kotlinevents.ui.list

import com.udacity.nano.popularmovies.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventListFragment : BaseFragment() {
    override val viewModel: EventListViewModel by viewModel()
}