package com.khairililmi.speakgayo.ui.news.screen.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khairililmi.speakgayo.ui.news.screen.data.TourismRepository
import com.khairililmi.speakgayo.ui.news.screen.model.Tourism
import com.khairililmi.speakgayo.ui.news.screen.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: TourismRepository) : ViewModel(){
    private val _uiState: MutableStateFlow<UiState<List<Tourism>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Tourism>>>
        get() = _uiState

    fun getAllRewards() {
        viewModelScope.launch {
            repository.getAllTourism()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { tourism ->
                    _uiState.value = UiState.Success(tourism)
                }
        }
    }
}