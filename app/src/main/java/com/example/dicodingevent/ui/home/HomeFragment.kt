package com.example.dicodingevent.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.EventAdapter
import com.example.dicodingevent.UpcomingAdapter
import com.example.dicodingevent.databinding.FragmentHomeBinding
import com.example.dicodingevent.ui.detail.DetailActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var upcomingAdapter: EventAdapter
    private lateinit var finishedAdapter: UpcomingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        upcomingAdapter = EventAdapter{ event ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("EVENT_ID", event.id)
            startActivity(intent)
        }

        binding.viewPagerUpcoming.adapter = upcomingAdapter

        finishedAdapter = UpcomingAdapter{ event ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("EVENT_ID", event.id)
            startActivity(intent)
        }

        binding.rvFinished.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFinished.adapter = finishedAdapter

        viewModel.upcomingEvents.observe(viewLifecycleOwner){ eventList ->
            upcomingAdapter.submitList(eventList)
        }

        viewModel.finishedEvents.observe(viewLifecycleOwner){ eventList ->
            finishedAdapter.submitList(eventList)
        }

        viewModel.isLoad.observe(viewLifecycleOwner){ isLoad ->
            showLoad(isLoad)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner){ errorMessage ->
            if (errorMessage != null){
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.fetchUpcomingEvents()
        viewModel.fetchFinishedEvents()
    }

    private fun showLoad(isLoad: Boolean) {
        binding.progressBar.visibility = if (isLoad) View.VISIBLE else View.GONE
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
