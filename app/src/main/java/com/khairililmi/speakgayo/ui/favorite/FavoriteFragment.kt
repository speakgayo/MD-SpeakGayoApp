package com.khairililmi.speakgayo.ui.favorite

import FavoriteAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.khairililmi.speakgayo.data.local.favorite.FavoriteEntity
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

        favoriteAdapter = FavoriteAdapter()
        val favoriteDao = AppFavoriteDb.getDatabase(requireContext()).favoriteDao()
         val repository = FavoriteRepository(favoriteDao)
        favoriteViewModel = ViewModelProvider(this, FavoriteViewModelFactory(repository)).get(FavoriteViewModel::class.java)

        binding.favoriteList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteAdapter
        }
        favoriteViewModel.getAllFavorites()

        favoriteViewModel.favorites.observe(viewLifecycleOwner) { favorites ->
            favoriteAdapter.setData(favorites)
        }
    }
}
