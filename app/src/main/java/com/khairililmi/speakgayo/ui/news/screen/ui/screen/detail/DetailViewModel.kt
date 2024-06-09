package com.khairililmi.speakgayo.ui.news.screen.ui.screen.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khairililmi.speakgayo.ui.news.screen.data.TourismRepository
import com.khairililmi.speakgayo.ui.news.screen.model.Schedule
import com.khairililmi.speakgayo.ui.news.screen.model.Tourism
import com.khairililmi.speakgayo.ui.news.screen.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: TourismRepository) : ViewModel() {
    private val _detailDataState: MutableStateFlow<UiState<Tourism>> =
        MutableStateFlow(UiState.Loading)
    val detailState: StateFlow<UiState<Tourism>> get() = _detailDataState

    var favoriteState by mutableStateOf(false)
    var dialogState by mutableStateOf(false)

    private val _listSelectedSchedule = mutableStateListOf<Int>()
    val listSelectedSchedule: List<Int> get() = _listSelectedSchedule

    fun updateFavoriteTourism(id: Int): String {
        val updateResult = repository.updateFavoriteTourism(id)
        isFavoriteTourism(id)
        return updateResult
    }

    fun updateScheduleTourism(schedule: Schedule) {
        if (listSelectedSchedule.contains(schedule.id)) {
            _listSelectedSchedule.remove(schedule.id)
        } else {
            _listSelectedSchedule.add(schedule.id)
        }
    }

    private fun isFavoriteTourism(id: Int) {
        val result = repository.getTourismById(id)
        favoriteState = result.isFavorite
    }

    fun getDetailById(id: Int) {
        viewModelScope.launch {
            _detailDataState.value = UiState.Loading
            try {
                val tourism = repository.getTourismById(id)
                _detailDataState.value = UiState.Success(tourism)
                isFavoriteTourism(id)
            } catch (e: Exception) {
                _detailDataState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
