package com.khairililmi.speakgayo.ui.history

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khairililmi.speakgayo.data.local.favorite.FavoriteEntity
import com.khairililmi.speakgayo.data.local.history.HistoryEntity
import com.khairililmi.speakgayo.ui.favorite.FavoriteRepository
import kotlinx.coroutines.launch

class HistoryViewModel (private val repository: HistoryRepository, private val applicationContext: Context
): ViewModel() {
    private val _history = MutableLiveData<List<HistoryEntity>>()
    val history: LiveData<List<HistoryEntity>> = _history

    private val _isHistoryDeleted = MutableLiveData<Boolean>()
    val isHistoryDeleted: LiveData<Boolean> = _isHistoryDeleted


    private val sharedPreferences =
        applicationContext.getSharedPreferences("favorite_status", Context.MODE_PRIVATE)

    private fun saveFavoriteStatus(historyId: Long, isFavorite: Boolean) {
        sharedPreferences.edit().putBoolean(historyId.toString(), isFavorite).apply()
    }

    private fun restoreFavoriteStatus(historyId: Long): Boolean {
        return sharedPreferences.getBoolean(historyId.toString(), false)
    }
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
            val histories = repository.getAllHistoriesSortedByIdDesc()
            histories.forEach { history ->
                val isFavorite = sharedPreferences.getBoolean(history.id.toString(), false)
                history.isFavorite = isFavorite
            }
            _history.value = histories
        }
    }
    fun insertFavorite(favoriteEntity: FavoriteEntity) {
        viewModelScope.launch {
            repository.addFavorite(favoriteEntity)
        }
    }
    fun deleteFavoriteById(favoriteId: Long) {
        viewModelScope.launch {
            repository.deleteFavoriteById(favoriteId)
        }
    }

    fun updateFavoriteStatus(history: HistoryEntity) {
        viewModelScope.launch {
            val newFavoriteStatus = !history.isFavorite

            try {
                if (newFavoriteStatus) {
                    val favoriteEntity = FavoriteEntity(
                        inLang = history.inLang,
                        inLangFavorite = history.inLangHistory,
                        gyLang = history.gyLang,
                        gyLangFavorite = history.gyLangHistory
                    )
                    repository.addFavorite(favoriteEntity)
                    Toast.makeText(applicationContext, "Item added to favorites", Toast.LENGTH_SHORT).show()
                } else {
                    repository.deleteFavoriteByValue(
                        history.inLang,
                        history.inLangHistory,
                        history.gyLang,
                        history.gyLangHistory
                    )
                    Toast.makeText(applicationContext, "Item removed from favorites", Toast.LENGTH_SHORT).show()
                }
                saveFavoriteStatus(history.id, newFavoriteStatus)
                history.isFavorite = newFavoriteStatus
                _history.value = _history.value?.map { if (it.id == history.id) history else it }
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Error updating item: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}