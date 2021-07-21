package com.github.andresvasquez.kotlinevents.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.andresvasquez.kotlinevents.databinding.FragmentEventListBinding
import com.github.andresvasquez.kotlinevents.ui.base.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class EventListFragment : BaseFragment() {
    override val viewModel: EventListViewModel by viewModel()

    private lateinit var adapter: EventListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEventListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = EventListAdapter(EventClickListener { event ->
            val navController = findNavController()
            navController.navigate(
                EventListFragmentDirections.actionEventListFragmentToEventDetailFragment(event)
            )
        })

        // Sets the adapter of the RecyclerView
        binding.eventsRecycler.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.loadEvents().collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
}