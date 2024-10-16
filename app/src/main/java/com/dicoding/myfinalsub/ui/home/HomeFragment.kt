package com.dicoding.myfinalsub.ui.home


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController // Import ini diperlukan
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.myfinalsub.databinding.FragmentHomeBinding
import com.dicoding.myfinalsub.ui.DetailActivity
import com.dicoding.myfinalsub.ui.HomeAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeAdapter: HomeAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()

        homeViewModel.events.observe(viewLifecycleOwner) { events ->
            homeAdapter.submitList(events)
        }

        homeViewModel.getEvents()
        return root
    }

    private fun setupRecyclerView() {
        // HomeAdapter sekarang menerima lambda untuk onItemClick
        homeAdapter = HomeAdapter { eventId ->
            // Buat Intent untuk membuka DetailActivity
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("EVENT_ID", eventId) // eventId bertipe Int
            }
            startActivity(intent) // Memulai DetailActivity
        }

        binding.rvHomeEvent.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
