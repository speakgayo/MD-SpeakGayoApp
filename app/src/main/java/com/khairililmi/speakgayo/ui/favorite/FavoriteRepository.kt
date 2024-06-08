package com.khairililmi.speakgayo.ui.favorite

import com.khairililmi.speakgayo.data.local.favorite.FavoriteDao
import com.khairililmi.speakgayo.data.local.favorite.FavoriteEntity

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity) {
        favoriteDao.deleteFavorite(favoriteEntity)
    }

    suspend fun getAllFavorites(): List<FavoriteEntity> {
        return favoriteDao.getAllFavorites()
    }

}
