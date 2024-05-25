package com.khairililmi.speakgayo.data.local.history

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface HistoryDao {
    @Insert
    suspend fun addHistory(history: HistoryEntity)

    @Query("SELECT * FROM history")
    suspend fun getAllHistory(): List<HistoryEntity>

    @Delete
    suspend fun deleteHistory(history: HistoryEntity)

    @Query("DELETE FROM history WHERE id = (SELECT MIN(id) FROM history)")
    suspend fun deleteOldestHistory()

    @Query("SELECT * FROM history ORDER BY id DESC")
    suspend fun getAllHistoriesSortedByIdDesc(): List<HistoryEntity>

}