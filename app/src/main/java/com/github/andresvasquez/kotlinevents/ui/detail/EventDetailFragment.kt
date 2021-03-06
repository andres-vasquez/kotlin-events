package com.github.andresvasquez.kotlinevents.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.andresvasquez.event_repository.data.Result
import com.github.andresvasquez.event_repository.model.EventListDomain
import com.github.andresvasquez.kotlinevents.databinding.FragmentEventDetailBinding
import com.github.andresvasquez.kotlinevents.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventDetailFragment : BaseFragment() {
    override val viewModel: EventDetailViewModel by viewModel()

    private val selectedEvent: EventListDomain by lazy {
        val args = EventDetailFragmentArgs.fromBundle(requireArguments())
        args.event
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEventDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.getEvent(selectedEvent.id).observe(viewLifecycleOwner, {
            if (it is Result.Success) {
                binding.event = it.data
            } else {
                viewModel.showErrorMessage.postValue(it.toString())
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }
}