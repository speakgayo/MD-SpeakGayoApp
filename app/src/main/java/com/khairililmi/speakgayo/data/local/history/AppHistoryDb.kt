package com.khairililmi.speakgayo.data.local.history

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [HistoryEntity::class], version = 2, exportSchema = false)
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
                )
//                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                return instance
            }
        }

//        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) { database.execSQL("ALTER TABLE history ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0")
//            }
//        }
    }
}
