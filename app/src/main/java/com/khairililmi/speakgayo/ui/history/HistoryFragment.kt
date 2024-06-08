package com.khairililmi.speakgayo.ui.history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.khairililmi.speakgayo.data.local.favorite.FavoriteEntity
import com.khairililmi.speakgayo.data.local.history.AppHistoryDb
import com.khairililmi.speakgayo.databinding.FragmentHistoryBinding
import com.khairililmi.speakgayo.ui.favorite.AppFavoriteDb

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historyViewModel:HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyAdapter = HistoryAdapter(
            onDeleteClickListener = { history ->
                binding.progressBar.visibility = View.VISIBLE
                historyViewModel.deleteHistory(history)
            },
            onFavoriteClickListener = { history ->
                binding.progressBar.visibility = View.VISIBLE
                historyViewModel.updateFavoriteStatus(history)
            }
        )


        val historyDao = AppHistoryDb.getDatabase(requireContext()).historyDao()
        val favoriteDao = AppFavoriteDb.getDatabase(requireContext()).favoriteDao()
        val repository = HistoryRepository(historyDao,favoriteDao)
        historyViewModel = ViewModelProvider(
            this,
            HistoryViewModelFactory(repository, requireContext().applicationContext)
        ).get(HistoryViewModel::class.java)

        binding.historyList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historyAdapter
        }
        binding.progressBar.visibility = View.VISIBLE
        binding.tvNotFound.visibility = View.GONE

        historyViewModel.getAllHistoriesSortedByIdDesc()
        historyViewModel.history.observe(viewLifecycleOwner) { history ->
            if (history.isEmpty()) {
                binding.tvNotFound.visibility = View.VISIBLE
            } else {
                historyAdapter.setData(history)
                binding.tvNotFound.visibility = View.GONE
            }
            binding.progressBar.visibility = View.GONE
        }
        historyViewModel.isHistoryDeleted.observe(viewLifecycleOwner) { isDeleted ->
            if (isDeleted) {
                historyViewModel.getAllHistory()
                binding.progressBar.visibility = View.VISIBLE
                historyViewModel.resetIsHistoryDeleted()
            }
        }

    }
}