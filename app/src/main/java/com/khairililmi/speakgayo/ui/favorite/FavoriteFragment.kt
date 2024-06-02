package com.khairililmi.speakgayo.ui.favorite

import FavoriteAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.khairililmi.speakgayo.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteAdapter = FavoriteAdapter { favorite ->
            binding.progressBar.visibility = View.VISIBLE
            favoriteViewModel.deleteFavorite(favorite)
        }

        val favoriteDao = AppFavoriteDb.getDatabase(requireContext()).favoriteDao()
         val repository = FavoriteRepository(favoriteDao)
        favoriteViewModel = ViewModelProvider(this, FavoriteViewModelFactory(repository)).get(FavoriteViewModel::class.java)

        binding.favoriteList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteAdapter
        }
        binding.progressBar.visibility = View.VISIBLE
        binding.tvNotFound.visibility = View.GONE

        favoriteViewModel.favorites.observe(viewLifecycleOwner) { favorites ->
            if (favorites.isEmpty()) {
                binding.tvNotFound.visibility = View.VISIBLE
            } else {
                favoriteAdapter.setData(favorites)
                binding.tvNotFound.visibility = View.GONE
            }
            binding.progressBar.visibility = View.GONE
        }
        favoriteViewModel.getAllFavorites()
        favoriteViewModel.isFavoriteDeleted.observe(viewLifecycleOwner) { isDeleted ->
            if (isDeleted) {
                favoriteViewModel.getAllFavorites()
                binding.progressBar.visibility = View.VISIBLE
                favoriteViewModel.resetIsFavoriteDeleted()
            }
        }

    }
}
