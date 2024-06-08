package com.khairililmi.speakgayo.ui.favorite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.khairililmi.speakgayo.data.local.favorite.FavoriteDao
import com.khairililmi.speakgayo.data.local.favorite.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class AppFavoriteDb : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: AppFavoriteDb? = null

        fun getDatabase(context: Context): AppFavoriteDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppFavoriteDb::class.java,
                    "app_favorite_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
