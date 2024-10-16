package com.dicoding.myfinalsub.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.myfinalsub.data.response.ListEventsItem
import com.dicoding.myfinalsub.databinding.ItemRowBinding

class FinishedEventAdapter(
    private val onItemClick: (Int) -> Unit // Tambahkan lambda untuk menangani klik item
) : ListAdapter<ListEventsItem, FinishedEventAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, onItemClick) // Pass onItemClick ke MyViewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val finishedEvent = getItem(position)
        holder.bind(finishedEvent)
    }

    class MyViewHolder(
        private val binding: ItemRowBinding,
        private val onItemClick: (Int) -> Unit // Menerima lambda untuk onItemClick
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(finishedEvent: ListEventsItem) {
            binding.eventTitle.text = finishedEvent.name
            Glide.with(binding.root.context)
                .load(finishedEvent.imageLogo)
                .into(binding.imgItem)

            itemView.setOnClickListener {
                finishedEvent.id?.let { eventId ->
                    onItemClick(eventId) // Panggil onItemClick saat item diklik
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
