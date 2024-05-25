package com.khairililmi.speakgayo.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khairililmi.speakgayo.data.local.favorite.FavoriteEntity
import kotlinx.coroutines.launch

class FavoriteViewModel (private val repository: FavoriteRepository): ViewModel()  {
    private val _favorites = MutableLiveData<List<FavoriteEntity>>()
    val favorites: LiveData<List<FavoriteEntity>> = _favorites

    private val _isFavoriteDeleted = MutableLiveData<Boolean>()
    val isFavoriteDeleted: LiveData<Boolean> = _isFavoriteDeleted

    fun deleteFavorite(favoriteEntity: FavoriteEntity) {
        viewModelScope.launch {
            repository.deleteFavorite(favoriteEntity)
            _isFavoriteDeleted.value = true
        }
    }
    fun resetIsFavoriteDeleted() {
        _isFavoriteDeleted.value = false
    }
    fun getAllFavorites() {
        viewModelScope.launch {
            _favorites.value = repository.getAllFavorites()
        }
    }

}

