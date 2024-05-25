package com.khairililmi.speakgayo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khairililmi.speakgayo.data.local.favorite.FavoriteEntity
import com.khairililmi.speakgayo.data.local.history.HistoryEntity
import com.khairililmi.speakgayo.data.remote.TranslateResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TranslateViewModel(private val repository: TranslateRepository) : ViewModel() {

    private val _translatedText = MutableLiveData<String>()
    val translatedText: LiveData<String> = _translatedText

    fun translateIndoToGayo(text: String) {
        repository.translateIndoToGayo(text, object : Callback<TranslateResponse> {
            override fun onResponse(call: Call<TranslateResponse>, response: Response<TranslateResponse>) {
                if (response.isSuccessful) {
                    _translatedText.postValue(response.body()?.translatedText ?: "Failed to translate")
                } else {
                    _translatedText.postValue("Failed to translate")
                }
            }

            override fun onFailure(call: Call<TranslateResponse>, t: Throwable) {
                _translatedText.postValue("Failed to translate")
            }
        })
    }

    fun translateGayoToIndo(text: String) {
        repository.translateGayoToIndo(text, object : Callback<TranslateResponse> {
            override fun onResponse(call: Call<TranslateResponse>, response: Response<TranslateResponse>) {
                if (response.isSuccessful) {
                    _translatedText.postValue(response.body()?.translatedText ?: "Failed to translate")
                } else {
                    _translatedText.postValue("Failed to translate")
                }
            }

            override fun onFailure(call: Call<TranslateResponse>, t: Throwable) {
                _translatedText.postValue("Failed to translate")
            }
        })
    }
    fun insertFavorite(favoriteEntity: FavoriteEntity) {
        viewModelScope.launch {
            repository.addFavorite(favoriteEntity)
        }
    }
    fun addHistory(historyEntity: HistoryEntity) {
        viewModelScope.launch {
            repository.addHistory(historyEntity)
            if (repository.getAllHistory().size > 7) {
                repository.deleteOldestHistory()
            }
        }
    }
}
