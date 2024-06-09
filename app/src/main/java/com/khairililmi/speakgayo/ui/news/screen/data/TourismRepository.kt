package com.khairililmi.speakgayo.ui.news.screen.data

import com.khairililmi.speakgayo.ui.news.screen.model.Tourism
import kotlinx.coroutines.flow.Flow

interface TourismRepository {
    fun getAllTourism(): Flow<List<Tourism>>
    fun getTourismById(id: Int): Tourism
    fun updateFavoriteTourism(id: Int): String
}