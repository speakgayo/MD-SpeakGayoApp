package com.khairililmi.speakgayo.data.local.history


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1, exportSchema = false)
abstract class AppHistoryDb : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppHistoryDb? = null

        fun getDatabase(context: Context): AppHistoryDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppHistoryDb::class.java,
                    "app_history_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
