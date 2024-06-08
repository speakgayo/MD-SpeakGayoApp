package com.khairililmi.speakgayo.data.local.history

import android.app.Application

class AppDb : Application() {
    override fun onCreate() {
        super.onCreate()
        AppHistoryDb.getDatabase(this)
    }
}