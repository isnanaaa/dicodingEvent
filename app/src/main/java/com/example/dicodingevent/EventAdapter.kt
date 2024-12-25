package com.example.dicodingevent

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevent.data.response.ListEventsItem
import com.example.dicodingevent.databinding.ItemUpcomingBinding

class EventAdapter(private val onItemClick: (ListEventsItem) -> Unit) : RecyclerView.Adapter<EventAdapter.UpcomingViewHolder>() {

    private var eventList: List<ListEventsItem> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UpcomingViewHolder {
        val binding = ItemUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UpcomingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        val event = eventList[position]
        holder.bind(event, onItemClick)
    }

    override fun getItemCount(): Int = eventList.size

    @SuppressLint("NotifydataChanged")
    fun submitList(events: List<ListEventsItem>){
        eventList = events
        notifyDataSetChanged()
    }

    class UpcomingViewHolder(private val binding: ItemUpcomingBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(event: ListEventsItem, onItemClick: (ListEventsItem) -> Unit){
            binding.eventName.text = event.name

            Glide.with(binding.root.context)
                .load(event.mediaCover)
                .into(binding.imgEvent)

            binding.root.setOnClickListener{
                onItemClick(event)
            }
        }
    }

}