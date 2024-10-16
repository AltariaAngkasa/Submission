package com.dicoding.myfinalsub.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.dicoding.myfinalsub.data.response.EventDetail
import com.dicoding.myfinalsub.data.response.ListEventsItem
import com.dicoding.myfinalsub.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventId = intent.getIntExtra("EVENT_ID", 0)
        Log.d("DetailActivity", "Received Event ID: $eventId")

        if (eventId != 0) {
            binding.progressBar.visibility = View.VISIBLE
            detailViewModel.getEventDetail(eventId)
            detailViewModel.eventDetail.observe(this) { eventDetail ->
                if (eventDetail != null) {
                    binding.progressBar.visibility = View.GONE
                    updateUI(eventDetail)
                } else {
                    Log.e("DetailActivity", "Event is null or data not available")
                    binding.tvName.text = "Event not found"
                    binding.progressBar.visibility = View.GONE
                }
            }
        } else {
            Log.e("DetailActivity", "Invalid Event ID")
            binding.tvName.text = "Invalid Event ID"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(eventDetail: ListEventsItem) {
        binding.tvName.text = eventDetail.name ?: "N/A"
        binding.tvSummary.text = eventDetail.summary ?: "N/A"
        binding.tvDescription.text = HtmlCompat.fromHtml(eventDetail.description ?: "No description available", HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.tvOwnerName.text = "Organizer: ${eventDetail.ownerName ?: "N/A"}"
        binding.tvBeginTime.text = "Event Time: ${eventDetail.beginTime ?: "N/A"}"

        // Menghitung sisa kuota
        val remainingQuota = (eventDetail.quota ?: 0) - (eventDetail.registrants ?: 0)
        binding.tvQuota.text = "Remaining Quota: $remainingQuota"

        // Menampilkan gambar cover menggunakan Glide
        Glide.with(this)
            .load(eventDetail.mediaCover ?: "")
            .into(binding.imgMediaCover)

        // Set onClick untuk membuka link event di browser
        binding.btnLink.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(eventDetail.link ?: ""))
            startActivity(browserIntent)
        }
    }
}
