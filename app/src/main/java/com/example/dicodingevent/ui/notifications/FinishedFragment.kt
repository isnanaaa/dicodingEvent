package com.example.dicodingevent.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.databinding.FragmentFinishedBinding
import com.example.dicodingevent.UpcomingAdapter
import com.example.dicodingevent.ui.detail.DetailActivity

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!

    private val finishedViewModel : FinishedViewModel by viewModels()
    private lateinit var eventAdapter: UpcomingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventAdapter = UpcomingAdapter { event ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("EVENT_ID", event.id)
            startActivity(intent)
        }

        binding.Finished.layoutManager = LinearLayoutManager(requireContext())
        binding.Finished.adapter = eventAdapter

        finishedViewModel.events.observe(viewLifecycleOwner){ eventList ->
            eventAdapter.submitList(eventList)
        }

        finishedViewModel.isLoad.observe(viewLifecycleOwner){ isLoad ->
            showLoad(isLoad)
        }

        finishedViewModel.errorMessage.observe(viewLifecycleOwner){ errorMessage ->
            if (errorMessage != null){
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
        finishedViewModel.fetchEvents()
    }

    private fun showLoad(isLoad: Boolean){
        if (isLoad){
            binding.progressBar.visibility = View.VISIBLE
        } else{
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
