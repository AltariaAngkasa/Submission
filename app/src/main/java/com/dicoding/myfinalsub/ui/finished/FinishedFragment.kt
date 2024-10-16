package com.dicoding.myfinalsub.ui.finished

import FinishedViewModel
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.myfinalsub.R
import com.dicoding.myfinalsub.databinding.FragmentFinishedBinding
import com.dicoding.myfinalsub.ui.DetailActivity
import com.dicoding.myfinalsub.ui.FinishedEventAdapter

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private lateinit var finishedEventAdapter: FinishedEventAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val finishedViewModel =
            ViewModelProvider(this)[FinishedViewModel::class.java]

        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()

        finishedViewModel.events.observe(viewLifecycleOwner) { events ->
            finishedEventAdapter.submitList(events)
        }

        finishedViewModel.getEvents()
        return root
    }

    private fun setupRecyclerView() {
        finishedEventAdapter = FinishedEventAdapter { eventId ->
            // Buat Intent untuk membuka DetailActivity
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("EVENT_ID", eventId) // eventId bertipe Int
            }
            startActivity(intent) // Memulai DetailActivity
        }

        binding.rvFinishedEvent.apply {
            adapter = finishedEventAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
