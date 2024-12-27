package com.example.dicodingevent.ui.fav

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.databinding.FragmentFavBinding
import com.example.dicodingevent.ui.FavAdapter
import com.example.dicodingevent.ui.detail.DetailActivity

class FavFragment : Fragment() {

    private var _binding: FragmentFavBinding? = null
    private val binding get() = _binding!!

    private val faViewModel: FavViewModel by viewModels()
    private lateinit var favAdapter: FavAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        faViewModel.isLoad.observe(viewLifecycleOwner){ isLoad ->
            showLoading(isLoad)
        }

        faViewModel.errorMessage.observe(viewLifecycleOwner){ errorMessage ->
            if (errorMessage != null){
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        favAdapter = FavAdapter{ event ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("EVENT_ID", event.id)
            startActivity(intent)
        }

        binding.rvFav.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFav.adapter = favAdapter

        faViewModel.favEventt.observe(viewLifecycleOwner){ favList ->
            if (favList.isNullOrEmpty()){
                binding.rvFav.visibility = View.GONE
            } else{
                binding.rvFav.visibility = View.VISIBLE
                favAdapter.submitList(favList)
            }
        }
    }

    private fun showLoading(isLoad: Boolean){
        if (isLoad){
            binding.progressBar.visibility = View.VISIBLE
        } else{
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}