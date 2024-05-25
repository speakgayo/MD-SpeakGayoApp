package com.khairililmi.speakgayo.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khairililmi.speakgayo.data.local.favorite.FavoriteEntity
import com.khairililmi.speakgayo.data.local.history.HistoryEntity
import com.khairililmi.speakgayo.ui.favorite.FavoriteRepository
import kotlinx.coroutines.launch

class HistoryViewModel (private val repository: HistoryRepository): ViewModel() {
    private val _history = MutableLiveData<List<HistoryEntity>>()
    val history: LiveData<List<HistoryEntity>> = _history

    private val _isHistoryDeleted = MutableLiveData<Boolean>()
    val isHistoryDeleted: LiveData<Boolean> = _isHistoryDeleted

    fun deleteHistory(historyEntity:HistoryEntity) {
        viewModelScope.launch {
            repository.deleteFavorite(historyEntity)
            _isHistoryDeleted.value = true
        }
    }
    fun resetIsHistoryDeleted() {
        _isHistoryDeleted.value = false
    }
    fun getAllHistory() {
        viewModelScope.launch {
            _history.value = repository.getAllHistory()
        }
    }
    fun getAllHistoriesSortedByIdDesc() {
        viewModelScope.launch {
            _history.value = repository.getAllHistoriesSortedByIdDesc()
        }
    }
    fun insertFavorite(favoriteEntity: FavoriteEntity) {
        viewModelScope.launch {
            repository.addFavorite(favoriteEntity)
        }
    }

}
