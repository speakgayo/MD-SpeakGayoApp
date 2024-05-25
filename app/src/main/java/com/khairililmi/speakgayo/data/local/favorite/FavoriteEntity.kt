package com.khairililmi.speakgayo.data.local.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val inLang: String,
    val inLangFavorite: String,
    val gyLang: String,
    val gyLangFavorite: String
)