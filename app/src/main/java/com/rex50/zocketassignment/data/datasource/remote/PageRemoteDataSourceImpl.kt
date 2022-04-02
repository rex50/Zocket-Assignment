package com.rex50.zocketassignment.data.datasource.remote

import com.rex50.zocketassignment.data.datasource.remote.services.MetaPageService
import com.rex50.zocketassignment.data.datasource.remote.services.ZocketService
import com.rex50.zocketassignment.data.models.Page
import com.rex50.zocketassignment.data.models.PageData
import com.rex50.zocketassignment.data.models.PagesResponse
import com.rex50.zocketassignment.data.models.SendPageDataResponse
import com.rex50.zocketassignment.utils.Result
import com.rex50.zocketassignment.utils.extensions.toResult
import javax.inject.Inject

class PageRemoteDataSourceImpl
@Inject constructor(
    private val metaPageService: MetaPageService,
    private val zocketService: ZocketService
){
    suspend fun getPages(accessToken: String): Result<PagesResponse> {
        // get pages from Facebook using ${accessToken}
        val response = metaPageService.getPages(accessToken = accessToken)
        return response.toResult()
    }

    suspend fun getPage(pageId: String, accessToken: String): Result<PageData> {
        // get single page details from facebook
        val response = metaPageService.getPage(
            pageId = pageId,
            accessToken = accessToken
        )
        return response.toResult()
    }

    suspend fun storePage(pageData: Page): Result<SendPageDataResponse> {
        // Send data to Zocket server
        val response = zocketService.sendPage(pageData)
        return response.toResult()
    }
}
