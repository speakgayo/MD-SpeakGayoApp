package com.khairililmi.speakgayo.data.local.favorite


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface FavoriteDao {
    @Insert
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): List<FavoriteEntity>

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity)

    @Query("SELECT * FROM favorites WHERE id = :id")
    suspend fun getFavoriteById(id: Long): FavoriteEntity?

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun deleteFavoriteById(id: Long)

    @Query("DELETE FROM favorites WHERE inLang = :inLang AND inLangFavorite = :inLangFavorite AND gyLang = :gyLang AND gyLangFavorite = :gyLangFavorite")
    suspend fun deleteFavoriteByValue(inLang: String, inLangFavorite: String, gyLang: String, gyLangFavorite: String): Int
}

