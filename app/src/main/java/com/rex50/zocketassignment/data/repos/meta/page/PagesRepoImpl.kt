package com.rex50.zocketassignment.data.repos.meta.page

import com.rex50.zocketassignment.data.datasource.local.sharedPrefs.userDetailProvider.UserDetailsProvider
import com.rex50.zocketassignment.data.datasource.local.room.PageLocalDataSourceImpl
import com.rex50.zocketassignment.data.datasource.remote.PageRemoteDataSourceImpl
import com.rex50.zocketassignment.data.datasource.remote.mappers.PageDataMapper
import com.rex50.zocketassignment.data.models.PageData
import com.rex50.zocketassignment.data.models.SendPageDataResponse
import com.rex50.zocketassignment.utils.*
import java.lang.Exception

class PagesRepoImpl(
    private val remoteDataSource: PageRemoteDataSourceImpl,
    private val localDataSource: PageLocalDataSourceImpl,
    private val userDetailsProvider: UserDetailsProvider
): PagesRepo {

    override suspend fun fetchPages(): Result<List<PageData>> {
        val accessToken = userDetailsProvider.getUserAccessToken()
        return if(accessToken != null) {
            when(val result = remoteDataSource.getPages(accessToken)) {
                is Result.Success -> {
                    Result.Success(result.data.data)
                }
                is Result.Failure -> result
            }
        } else
            Result.Failure(IllegalAccessException("Access token is null"))
    }

    override suspend fun updatePage(pageData: PageData): Result<PageData> {
        return remoteDataSource.getPage(
            pageId = pageData.id,
            accessToken = userDetailsProvider.getUserAccessToken() ?: ""
        )
    }

    override suspend fun sendPageData(pageData: PageData): Result<SendPageDataResponse> {
        val mappedPageData = PageDataMapper.pageDataToPage(pageData)
        return remoteDataSource.storePage(mappedPageData)
    }

    override suspend fun cachePage(pageData: PageData) {
        localDataSource.storePageDetails(pageData)
    }

    override suspend fun cachePages(pageData: List<PageData>) {
        localDataSource.storePages(pageData)
    }

    override suspend fun getSelectedPage(): Result<PageData> {
        val selectedId = userDetailsProvider.getSelectedPageId()
        val result = localDataSource.getPageDetails(selectedId)
        return if(result.isNotEmpty())
            Result.Success(result.first())
        else
            Result.Failure(Exception("No results found"))
    }

    override suspend fun setSelectedPage(pageId: String) {
        userDetailsProvider.updateSelectedPageId(pageId)
    }

}