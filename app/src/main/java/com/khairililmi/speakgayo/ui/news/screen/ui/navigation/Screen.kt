package com.khairililmi.speakgayo.ui.news.screen.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AboutMe : Screen("about_me")
    object Detail : Screen("detail/{id}") {
        fun createRoute(id: Int) = "detail/$id"
    }
}