package com.github.andresvasquez.kotlinevents.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.andresvasquez.event_repository.model.EventListDomain
import com.github.andresvasquez.kotlinevents.databinding.ListItemBinding

class EventListAdapter(val clickListener: EventClickListener) :
    PagingDataAdapter<EventListDomain, EventListAdapter.EventListViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<EventListDomain>() {
        override fun areItemsTheSame(
            oldItem: EventListDomain,
            newItem: EventListDomain
        ): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(
            oldItem: EventListDomain,
            newItem: EventListDomain
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }


    class EventListViewHolder(private var binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: EventClickListener, event: EventListDomain) {
            binding.event = event
            binding.clickListener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): EventListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return EventListViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder {
        return EventListViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        val event: EventListDomain? = getItem(position)
        event?.let { holder.bind(clickListener, it) }
    }

    fun getItemByPosition(position: Int): EventListDomain? {
        return getItem(position)
    }
}

class EventClickListener(val clickListener: (event: EventListDomain) -> Unit) {
    fun onClick(event: EventListDomain) = clickListener(event)
}