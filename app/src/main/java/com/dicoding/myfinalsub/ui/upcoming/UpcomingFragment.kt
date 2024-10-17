package com.dicoding.myfinalsub.ui.upcoming

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.myfinalsub.databinding.FragmentUpcomingBinding
import com.dicoding.myfinalsub.ui.DetailActivity
import com.dicoding.myfinalsub.ui.UpcomingAdapter
import com.dicoding.myfinalsub.ui.upcoming.UpcomingViewModel

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private lateinit var upcomingAdapter: UpcomingAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val upcomingViewModel =
            ViewModelProvider(this)[UpcomingViewModel::class.java]

        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        upcomingViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }


        upcomingViewModel.events.observe(viewLifecycleOwner) { events ->
            upcomingAdapter.submitList(events)
        }

//        val rvEvents: RecyclerView = binding.rvFinishedEvent
//        finishedViewModel.events.observe(viewLifecycleOwner) {
//            rvEvents.setHasFixedSize(true)
//        }

        upcomingViewModel.getEvents()
        return root
    }

    private fun setupRecyclerView() {
        upcomingAdapter = UpcomingAdapter { eventId ->
            // Buat Intent untuk membuka DetailActivity
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("EVENT_ID", eventId) // eventId bertipe Int
            }
            startActivity(intent) // Memulai DetailActivity
        }

        binding.rvUpcomingEvent.apply {
            adapter = upcomingAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}