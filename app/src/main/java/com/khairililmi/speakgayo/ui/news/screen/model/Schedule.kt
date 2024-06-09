package com.khairililmi.speakgayo.ui.news.screen.model

data class Schedule(
    val id: Int,
    val day: String,
    val date: String,
    val isAvailable: Boolean,
    var isSelected: Boolean
)