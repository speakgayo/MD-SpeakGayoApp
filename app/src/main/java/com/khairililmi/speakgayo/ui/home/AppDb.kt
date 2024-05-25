package com.khairililmi.speakgayo.ui.home

import com.khairililmi.speakgayo.ui.favorite.AppFavoriteDb


import android.app.Application


class AppDb : Application() {
    override fun onCreate() {
        super.onCreate()
        AppFavoriteDb.getDatabase(this)
    }
}