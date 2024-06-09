package com.khairililmi.speakgayo.ui.history

import android.util.Log
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
    suspend fun deleteFavoriteByHistory(historyEntity: HistoryEntity) {
        favoriteDao.deleteFavorite(
            FavoriteEntity(
                inLang = historyEntity.inLang,
                inLangFavorite = historyEntity.inLangHistory,
                gyLang = historyEntity.gyLang,
                gyLangFavorite = historyEntity.gyLangHistory
            )
        )
    }
    suspend fun deleteFavoriteById(id: Long) {
        try {
            val deletedRows = favoriteDao.deleteFavoriteById(id)
            Log.d("FavoriteRepository", "Deleted $deletedRows row(s) from favorites")
        } catch (e: Exception) {
            Log.e("FavoriteRepository", "Error deleting favorite with ID $id: ${e.message}")
        }
    }
    suspend fun deleteFavoriteByValue(inLang: String, inLangFavorite: String, gyLang: String, gyLangFavorite: String) {
        favoriteDao.deleteFavoriteByValue(inLang, inLangFavorite, gyLang, gyLangFavorite)
    }

}