package com.khairililmi.speakgayo.ui.favorite

import com.khairililmi.speakgayo.data.local.favorite.FavoriteDao
import com.khairililmi.speakgayo.data.local.favorite.FavoriteEntity

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    suspend fun addFavorite(favoriteEntity: FavoriteEntity) {
        favoriteDao.addFavorite(favoriteEntity)
    }

    suspend fun getAllFavorites(): List<FavoriteEntity> {
        return favoriteDao.getAllFavorites()
    }
}
