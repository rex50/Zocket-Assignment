package com.rex50.zocketassignment.data.datasource.local.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rex50.zocketassignment.data.models.PageData

@Dao
interface PagesDao {

    @Query("SELECT * FROM pagedata WHERE id = :pageId")
    fun getPage(pageId: String): List<PageData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun storePages(pageData: List<PageData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun storePage(pageData: PageData)

}