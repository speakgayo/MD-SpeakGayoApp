package com.khairililmi.speakgayo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TranslateViewModelFactory(private val repository: TranslateRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TranslateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TranslateViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
