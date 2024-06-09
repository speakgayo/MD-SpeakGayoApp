package com.khairililmi.speakgayo.ui.news.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khairililmi.speakgayo.ui.news.screen.data.TourismRepository
import com.khairililmi.speakgayo.ui.news.screen.ui.screen.detail.DetailViewModel
import com.khairililmi.speakgayo.ui.news.screen.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: TourismRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}