package com.khairililmi.speakgayo.data.local.history

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
class HistoryEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val inLang: String,
    val inLangHistory: String,
    val gyLang: String,
    val gyLangHistory: String,
    var isFavorite: Boolean = false
)