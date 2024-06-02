package com.khairililmi.speakgayo.ui.history

import com.khairililmi.speakgayo.data.local.favorite.FavoriteDao
import com.khairililmi.speakgayo.data.local.favorite.FavoriteEntity
import com.khairililmi.speakgayo.data.local.history.HistoryDao
import com.khairililmi.speakgayo.data.local.history.HistoryEntity

class HistoryRepository(private val historyDao: HistoryDao, private val favoriteDao: FavoriteDao) {

    suspend fun deleteFavorite(historyEntity: HistoryEntity) {
        historyDao.deleteHistory(historyEntity)
    }
    suspend fun getAllHistory(): List<HistoryEntity> {
        return historyDao.getAllHistory()
    }
    suspend fun getAllHistoriesSortedByIdDesc(): List<HistoryEntity> {
        return historyDao.getAllHistoriesSortedByIdDesc()
    }
    suspend fun addFavorite(favoriteEntity: FavoriteEntity) {
        favoriteDao.addFavorite(favoriteEntity)
    }

}