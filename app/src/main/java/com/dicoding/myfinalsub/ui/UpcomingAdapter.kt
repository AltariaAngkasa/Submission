package com.dicoding.myfinalsub.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.myfinalsub.data.response.ListEventsItem
import com.dicoding.myfinalsub.databinding.ItemRowBinding

class UpcomingAdapter(
    private val onItemClick: (Int) -> Unit // Tambahkan lambda untuk menangani klik item
) : ListAdapter<ListEventsItem, UpcomingAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, onItemClick) // Pass onItemClick ke MyViewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val upcomingEvent = getItem(position)
        holder.bind(upcomingEvent)
    }

    class MyViewHolder(
        private val binding: ItemRowBinding,
        private val onItemClick: (Int) -> Unit // Menerima lambda untuk onItemClick
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(upcomingEvent: ListEventsItem) {
            binding.eventTitle.text = upcomingEvent.name
            Glide.with(binding.root.context)
                .load(upcomingEvent.imageLogo)
                .into(binding.imgItem)

            itemView.setOnClickListener {
                upcomingEvent.id?.let { eventId ->
                    onItemClick(eventId) // Panggil onItemClick saat item diklik
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem.id == newItem.id // Pastikan bahwa ListEventsItem memiliki properti 'id'
            }

            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
