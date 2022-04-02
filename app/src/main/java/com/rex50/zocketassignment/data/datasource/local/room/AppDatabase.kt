package com.rex50.zocketassignment.data.datasource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rex50.zocketassignment.data.datasource.local.room.convertors.EmailsTypeConvertor
import com.rex50.zocketassignment.data.datasource.local.room.daos.PagesDao
import com.rex50.zocketassignment.data.models.PageData

@TypeConverters(EmailsTypeConvertor::class)
@Database(entities = [PageData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pagesDao(): PagesDao
}