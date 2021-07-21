package com.github.andresvasquez.kotlinevents.ui.list

import android.os.Bundle
import android.view.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.andresvasquez.kotlinevents.R
import com.github.andresvasquez.kotlinevents.databinding.FragmentEventListBinding
import com.github.andresvasquez.kotlinevents.ui.MainActivity
import com.github.andresvasquez.kotlinevents.ui.base.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class EventListFragment : BaseFragment() {
    override val viewModel: EventListViewModel by viewModel()

    lateinit var adapter: EventListAdapter

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
        setHasOptionsMenu(true);
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about_menu -> {
                val mainActivity = activity as MainActivity
                mainActivity.showAboutDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}