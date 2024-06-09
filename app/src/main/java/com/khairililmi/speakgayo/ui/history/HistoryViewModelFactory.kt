package com.khairililmi.speakgayo.ui.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khairililmi.speakgayo.ui.favorite.FavoriteRepository
import com.khairililmi.speakgayo.ui.favorite.FavoriteViewModel

class HistoryViewModelFactory(private val repository: HistoryRepository, private val applicationContext: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(repository,applicationContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}