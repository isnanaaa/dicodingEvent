package com.example.dicodingevent.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevent.data.FavEvent
import com.example.dicodingevent.databinding.ItemEventBinding

class FavAdapter(private val onItemClick: (FavEvent) -> Unit) :
ListAdapter<FavEvent, FavAdapter.FaViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):FaViewHolder{
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FaViewHolder, position: Int){
        val fav = getItem(position)
        holder.bind(fav, onItemClick)
    }

    class FaViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(fav: FavEvent, onItemClick: (FavEvent) -> Unit){
            binding.eventName.text = fav.name
            binding.detail.text = "Event Favorit"

            Glide.with(binding.imgEvent.context)
                .load(fav.mediaCover)
                .into(binding.imgEvent)

            binding.root.setOnClickListener{
                onItemClick(fav)
            }
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavEvent>(){
            override fun areItemsTheSame(oldItem: FavEvent, newItem: FavEvent): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FavEvent, newItem: FavEvent): Boolean {
                return oldItem == newItem
            }
        }
    }
}