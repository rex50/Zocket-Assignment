package com.rex50.zocketassignment.data.datasource.local.room

import com.rex50.zocketassignment.data.datasource.local.room.daos.PagesDao
import com.rex50.zocketassignment.data.models.PageData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PageLocalDataSourceImpl
@Inject
constructor(
    private val dao: PagesDao
) {

    suspend fun getPageDetails(pageId: String): List<PageData> = withContext(Dispatchers.IO) {
        // get page from DB
        return@withContext dao.getPage(pageId = pageId)
    }

    suspend fun storePageDetails(pageData: PageData) {
        withContext(Dispatchers.IO) {
            // Store page in DB
            dao.storePage(pageData)
        }
    }

    suspend fun storePages(pageData: List<PageData>) {
        withContext(Dispatchers.IO) {
            // Store pages in DB
            dao.storePages(pageData)
        }
    }
}