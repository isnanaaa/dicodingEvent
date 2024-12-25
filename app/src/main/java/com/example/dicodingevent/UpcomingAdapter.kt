package com.example.dicodingevent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevent.data.response.ListEventsItem
import com.example.dicodingevent.databinding.ItemEventBinding

class UpcomingAdapter(
    private val onItemClick: (ListEventsItem) -> Unit) : ListAdapter<ListEventsItem, UpcomingAdapter.MyViewHolder> (
        DIFF_CALLBACK
    ){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val event = getItem(position)
            holder.bind(event, onItemClick)
        }

        class MyViewHolder(val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(event: ListEventsItem, onItemClick: (ListEventsItem) -> Unit){
                binding.eventName.text = event.name
                binding.detail.text = event.summary

                Glide.with(binding.imageEvent.context)
                    .load(event.mediaCover)
                    .into(binding.imageEvent)

                binding.root.setOnClickListener{
                    onItemClick(event)
                }
            }
        }
        companion object {
            val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
                override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                    return oldItem == newItem
                }
                override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
