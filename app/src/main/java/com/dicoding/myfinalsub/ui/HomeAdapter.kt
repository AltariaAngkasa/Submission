package com.dicoding.myfinalsub.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.load.DataSource
import com.dicoding.myfinalsub.R
import com.dicoding.myfinalsub.data.response.ListEventsItem
import com.dicoding.myfinalsub.databinding.ItemRowBinding


class HomeAdapter(private val onItemClick: (Int) -> Unit) :
    ListAdapter<ListEventsItem, HomeAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    class MyViewHolder(
        private val binding: ItemRowBinding,
        private val onItemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: ListEventsItem) {
            binding.eventTitle.text = event.name
            binding.progressBar.visibility = View.GONE
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .placeholder(R.drawable.image_placeholder) // Gambar sementara saat dimuat
                .error(R.drawable.broken_image) // Gambar error saat gagal dimuat
                .into(binding.imgItem)


            itemView.setOnClickListener {
                event.id?.let { eventId ->
                    onItemClick(eventId)
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
