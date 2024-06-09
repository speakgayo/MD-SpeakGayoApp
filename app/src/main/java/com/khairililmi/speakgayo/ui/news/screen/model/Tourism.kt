package com.khairililmi.speakgayo.ui.news.screen.model

data class Tourism(
    val id: Int,
    val name: String,
    val location: String,
    val rate: String,
    val description: String,
    val ticketPrice: String,
    val picture: Int,
    val schedule: List<Schedule>,
    val isFavorite: Boolean
)