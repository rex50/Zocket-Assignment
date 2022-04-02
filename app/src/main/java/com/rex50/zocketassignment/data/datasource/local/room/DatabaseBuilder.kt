package com.rex50.zocketassignment.data.datasource.local.room

import android.content.Context
import androidx.room.Room

class DatabaseBuilder(
    private val context: Context
) {

    private var appDatabase: AppDatabase? = null

    fun getInstance(): AppDatabase {
        if (appDatabase == null) {
            synchronized(AppDatabase::class) {
                appDatabase = buildRoomDB()
            }
        }

        return appDatabase!!
    }

    private fun buildRoomDB(): AppDatabase {
        return Room
            .databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "zocket_database"
            )
            .fallbackToDestructiveMigration()
            .build()
    }
}
