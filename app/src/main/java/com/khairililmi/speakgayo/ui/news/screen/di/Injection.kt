package com.khairililmi.speakgayo.ui.news.screen.di

import com.khairililmi.speakgayo.ui.news.screen.data.TourismRepository
import com.khairililmi.speakgayo.ui.news.screen.data.TourismRepositoryImpl

object Injection {
    fun provideRepository(): TourismRepository {
        return TourismRepositoryImpl.getInstance()
    }
}