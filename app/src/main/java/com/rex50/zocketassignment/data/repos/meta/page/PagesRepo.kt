package com.rex50.zocketassignment.data.repos.meta.page

import com.rex50.zocketassignment.data.models.PageData
import com.rex50.zocketassignment.data.models.SendPageDataResponse
import com.rex50.zocketassignment.utils.Result

interface PagesRepo {

    suspend fun fetchPages(): Result<List<PageData>>

    suspend fun updatePage(pageData: PageData): Result<PageData>

    suspend fun sendPageData(pageData: PageData): Result<SendPageDataResponse>

    suspend fun cachePage(pageData: PageData)

    suspend fun cachePages(pageData: List<PageData>)

    suspend fun getSelectedPage(): Result<PageData>

    suspend fun setSelectedPage(pageId: String)

}